package com.cioffi.salarymanagement.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class homeUiState(
    val fixedNeeds: Int = 0,  //50%
    val wants: Int = 0,       //30%
    val savings: Int = 0      //20%
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
        val sal = salary.toInt()
        if(sal > 0 ) {
            _uiState.update { it ->
                it.copy(
                    fixedNeeds = sal *50/100,
                    wants = sal *30/100,
                    savings = sal *20/100
                )
            }
        }
    }

    fun onClear() {
        updateSalary("")
            _uiState.update { it ->
                it.copy(
                    fixedNeeds = 0,
                    wants = 0,
                    savings = 0
                )
            }
    }
}