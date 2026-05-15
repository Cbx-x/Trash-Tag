package com.mindmatrix.app1.model

import com.google.firebase.Timestamp

data class WasteReport(
    val id: String = "",
    val reporterId: String = "",
    val title: String = "",
    val locationName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val wasteType: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val status: String = "Pending", // "Pending", "In Progress", "Resolved"
    val timestamp: Timestamp = Timestamp.now()
)
