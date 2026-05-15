package com.mindmatrix.app1.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mindmatrix.app1.model.WasteReport
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class WasteRepository {
    private val db = FirebaseFirestore.getInstance()
    private val reportsCollection = db.collection("reports")

    suspend fun submitReport(report: WasteReport): Result<Unit> {
        return try {
            val docRef = reportsCollection.document()
            val finalReport = report.copy(id = docRef.id)
            docRef.set(finalReport).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getAllReports(): Flow<List<WasteReport>> = callbackFlow {
        val subscription = reportsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("WasteRepository", "Query with sort failed: ${error.message}. Falling back to client-side sort.")
                    // Fallback: Fetch without order and sort on client if index is missing
                    reportsCollection.addSnapshotListener { fallbackSnapshot, fallbackError ->
                        if (fallbackError != null) {
                            Log.e("WasteRepository", "Fallback query failed: ${fallbackError.message}")
                            return@addSnapshotListener
                        }
                        val reports = fallbackSnapshot?.toObjects(WasteReport::class.java) ?: emptyList()
                        trySend(reports.sortedByDescending { it.timestamp })
                    }
                    return@addSnapshotListener
                }
                val reports = snapshot?.toObjects(WasteReport::class.java) ?: emptyList()
                trySend(reports)
            }
        awaitClose { subscription.remove() }
    }

    suspend fun updateReportStatus(reportId: String, newStatus: String): Result<Unit> {
        return try {
            reportsCollection.document(reportId).update("status", newStatus).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
