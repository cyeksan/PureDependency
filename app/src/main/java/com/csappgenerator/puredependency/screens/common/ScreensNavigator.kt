package com.csappgenerator.puredependency.screens.common

import androidx.appcompat.app.AppCompatActivity
import com.csappgenerator.puredependency.R
import com.csappgenerator.puredependency.screens.questiondetails.QuestionDetailsFragment
import com.csappgenerator.puredependency.screens.questionslist.QuestionsListFragment

class ScreensNavigator(
    private val activity: AppCompatActivity
) {
    fun navigateToDetailScreen(id: String) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_content, QuestionDetailsFragment.newInstance(id))
            .commit()

    }

    fun navigateBack() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_content, QuestionsListFragment.newInstance())
            .commit()
    }
}