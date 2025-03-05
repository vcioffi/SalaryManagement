package com.cioffi.salarymanagement

import android.app.Application
import com.cioffi.salarymanagement.data.AppContainer

class SalaryMgtApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
//        container = AppContainer(this)
   }
}