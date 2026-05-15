package com.mindmatrix.app1.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindmatrix.app1.model.WasteReport
import com.mindmatrix.app1.repository.WasteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WasteViewModel(private val repository: WasteRepository = WasteRepository()) : ViewModel() {
    private val _reports = MutableStateFlow<List<WasteReport>>(emptyList())
    val reports: StateFlow<List<WasteReport>> = _reports.asStateFlow()

    private val _submissionState = mutableStateOf<Result<Unit>?>(null)
    val submissionState: State<Result<Unit>?> = _submissionState

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    init {
        fetchReports()
    }

    private fun fetchReports() {
        viewModelScope.launch {
            repository.getAllReports()
                .catch { e ->
                    // Log error and set empty list to prevent crashes/stuck UI
                    _reports.value = emptyList()
                }
                .collect {
                    _reports.value = it
                }
        }
    }

    fun submitReport(report: WasteReport) {
        viewModelScope.launch {
            _loading.value = true
            _submissionState.value = repository.submitReport(report)
            _loading.value = false
        }
    }

    fun updateStatus(reportId: String, newStatus: String) {
        viewModelScope.launch {
            repository.updateReportStatus(reportId, newStatus)
        }
    }
}
