package com.csappgenerator.puredependency.common.di

import android.view.LayoutInflater
import com.csappgenerator.puredependency.questions.FetchQuestionDetailsUseCase
import com.csappgenerator.puredependency.questions.FetchQuestionsUseCase
import com.csappgenerator.puredependency.screens.common.ScreensNavigator
import com.csappgenerator.puredependency.screens.common.dialogs.DialogNavigator
import com.csappgenerator.puredependency.screens.common.viewsmvc.ViewMvcFactory

class PresentationCompositionRoot(
    private val activityCompositionRoot: ActivityCompositionRoot
) {
    val screensNavigator: ScreensNavigator get() = activityCompositionRoot.screensNavigator
    private val layoutInflater: LayoutInflater get() = activityCompositionRoot.layoutInflater
    private val supportFragmentManager get() = activityCompositionRoot.supportFragmentManager
    private val stackOverflowApi get() = activityCompositionRoot.stackOverflowApi
    val dialogNavigator: DialogNavigator get() = DialogNavigator(supportFragmentManager)
    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackOverflowApi)
    val fetchQuestionDetailsUseCase get() = FetchQuestionDetailsUseCase(stackOverflowApi)
    val viewMvcFactory: ViewMvcFactory get() = ViewMvcFactory(layoutInflater)
}