package com.csappgenerator.puredependency.common.di

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.csappgenerator.puredependency.networking.StackoverflowApi
import com.csappgenerator.puredependency.screens.common.ScreensNavigator

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    appCompositionRoot: AppCompositionRoot

) {
    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(activity)
    }

    val layoutInflater: LayoutInflater get() = activity.layoutInflater

    val supportFragmentManager get() = activity.supportFragmentManager

    val stackOverflowApi: StackoverflowApi = appCompositionRoot.stackOverflowApi

}