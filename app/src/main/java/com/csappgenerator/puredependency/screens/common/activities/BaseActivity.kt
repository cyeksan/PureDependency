package com.csappgenerator.puredependency.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.csappgenerator.puredependency.MyApplication
import com.csappgenerator.puredependency.common.di.ActivityCompositionRoot

open class BaseActivity: AppCompatActivity() {
    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot
    val activityCompositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot(
            this,
            appCompositionRoot
        )
    }
}