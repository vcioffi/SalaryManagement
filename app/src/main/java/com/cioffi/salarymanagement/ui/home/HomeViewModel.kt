package com.cioffi.salarymanagement.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat

data class homeUiState(
    val fixedNeeds: Double = 0.0,  //50%
    val wants: Double = 0.0,       //30%
    val savings: Double = 0.0      //20%
)

class HomeViewModel :ViewModel() {

    private val _uiState = MutableStateFlow(homeUiState())
    val uiState: StateFlow<homeUiState> = _uiState.asStateFlow()

    var salary by mutableStateOf("")
        private set


    fun updateSalary(sal: String){
        salary = sal
    }

    fun calcPercentage() {
        if (salary.isNotEmpty() && salary.toDouble() > 0.0 ) {
            val sal = salary.toDouble()
            _uiState.update { it ->
                it.copy(
                    fixedNeeds = sal * 50 / 100,
                    wants = sal * 30 / 100,
                    savings = sal * 20 / 100
                )
            }
        }
    }

    fun onClear() {
        updateSalary("")
            _uiState.update { it ->
                it.copy(
                    fixedNeeds = 0.0,
                    wants = 0.0,
                    savings = 0.0
                )
            }
    }
}