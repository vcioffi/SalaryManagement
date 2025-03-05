package com.cioffi.salarymanagement

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cioffi.salarymanagement.ui.home.HomeViewModel

class AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel()
        }
    }
}
