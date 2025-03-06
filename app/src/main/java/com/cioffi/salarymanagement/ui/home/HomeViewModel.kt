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
    val fixedNeeds: Double = 0.0,
    val wants: Double = 0.0,
    val savings: Double = 0.0,
    val fixedNeedsPercentage: Float = 50f,  //50%
    val wantsPercentage: Float = 30f,       //30%
    val savingsPercentage: Float = 20f      //20%
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
                    fixedNeeds = sal * uiState.value.fixedNeedsPercentage / 100,
                    wants = sal * uiState.value.wantsPercentage / 100,
                    savings = sal * uiState.value.savingsPercentage / 100
                )
            }
        }
    }

    fun onNeedsPercChange(perc :Float){
        _uiState.update {it.copy( fixedNeedsPercentage = perc )}
    }

    fun onWantsPercChange(perc :Float){
        _uiState.update {it.copy( wantsPercentage = perc )}
    }

    fun onSavingPercChange(perc :Float){
        _uiState.update {it.copy( savingsPercentage = perc )}
    }

    fun balancePercentage(){

    }

    fun onClear() {
        updateSalary("")
            _uiState.update { it ->
                it.copy(
                    fixedNeeds = 0.0,
                    wants = 0.0,
                    savings = 0.0,
                    fixedNeedsPercentage = 50f,
                    wantsPercentage = 30f,
                    savingsPercentage = 20f
                )
            }
    }
}