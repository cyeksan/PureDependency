package com.csappgenerator.puredependency

import android.app.Application
import com.csappgenerator.puredependency.common.di.AppCompositionRoot

class MyApplication: Application() {

    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot()
        super.onCreate()
    }
}