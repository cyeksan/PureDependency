package com.csappgenerator.puredependency.screens.common.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.csappgenerator.puredependency.screens.questiondetails.QuestionDetailsViewMvc
import com.csappgenerator.puredependency.screens.questionslist.QuestionListViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {
    fun newQuestionListViewMvc(container: ViewGroup?): QuestionListViewMvc {
        return QuestionListViewMvc(layoutInflater, container)
    }

    fun newQuestionDetailsViewMvc(container: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvc(layoutInflater, container)
    }
}