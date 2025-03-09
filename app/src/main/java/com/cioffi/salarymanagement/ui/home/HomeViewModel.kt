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
    val isSlidersEnable: Boolean = false,
    val fixedNeeds: Double = 0.0,
    val wants: Double = 0.0,
    val savings: Double = 0.0,
    val fixedNeedsPercentage: Float = 50f,  //50%
    val wantsPercentage: Float = 30f,       //30%
    val savingsPercentage: Float = 20f,      //20%
    val totalPercentage: Int = 100
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

    private fun calcTotalPercentage() {
        _uiState.update { it ->
            it.copy(
                totalPercentage = (_uiState.value.wantsPercentage + _uiState.value.savingsPercentage + _uiState.value.fixedNeedsPercentage).toInt()
            )
        }
    }

    fun onNeedsPercChange(perc :Float){
        _uiState.update {it.copy( fixedNeedsPercentage = perc )}
        calcTotalPercentage()
    }

    fun onWantsPercChange(perc :Float){
        _uiState.update {it.copy( wantsPercentage = perc )}
        calcTotalPercentage()
    }

    fun onSavingPercChange(perc :Float){
        _uiState.update {it.copy( savingsPercentage = perc )}
        calcTotalPercentage()
    }

    fun onLockClick(){
        if(_uiState.value.isSlidersEnable == false){
            _uiState.update { it.copy( isSlidersEnable = true ) }
        } else{
            _uiState.update {  it.copy( isSlidersEnable = false ) }
            onResetPercentage()
        }
    }

    fun onResetPercentage(){
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

    fun onClear() {
        updateSalary("")
        onResetPercentage()
    }
}