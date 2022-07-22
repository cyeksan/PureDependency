package com.csappgenerator.puredependency.screens.questiondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.csappgenerator.puredependency.common.di.Service
import com.csappgenerator.puredependency.questions.FetchQuestionDetailsUseCase
import com.csappgenerator.puredependency.screens.common.ScreensNavigator
import com.csappgenerator.puredependency.screens.common.dialogs.DialogNavigator
import com.csappgenerator.puredependency.screens.common.fragments.BaseFragment
import com.csappgenerator.puredependency.screens.common.viewsmvc.ViewMvcFactory
import kotlinx.coroutines.*


class QuestionDetailsFragment : BaseFragment(), QuestionDetailsViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String
    private lateinit var questionDetailsViewMvc: QuestionDetailsViewMvc
    @field:Service private lateinit var viewMvcFactory: ViewMvcFactory
    @field:Service private lateinit var fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
    @field:Service private lateinit var dialogNavigator: DialogNavigator
    @field:Service private lateinit var screensNavigator: ScreensNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
        // retrieve question ID passed from outside
        questionId = arguments!!.getString(QUESTION_ID).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        questionDetailsViewMvc = viewMvcFactory.newQuestionDetailsViewMvc(container)
        return questionDetailsViewMvc.root
    }

    override fun onStart() {
        super.onStart()
        questionDetailsViewMvc.registerListener(this)
        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()
        questionDetailsViewMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            try {
                questionDetailsViewMvc.showProgressIndication()
                when (val result = fetchQuestionDetailsUseCase.fetchQuestionDetails(questionId)) {
                    is FetchQuestionDetailsUseCase.Result.Success -> {
                        questionDetailsViewMvc.bindQuestionBody(result.questionDetail.body)
                    }
                    is FetchQuestionDetailsUseCase.Result.Error -> onFetchFailed()
                }
            } finally {
                questionDetailsViewMvc.hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        dialogNavigator.showServerErrorDialog()
    }

    override fun onBackClicked() {
        screensNavigator.navigateBack()
    }

    companion object {
        const val QUESTION_ID = "questionId"
        fun newInstance(questionId: String?): Fragment {
            val fragment = QuestionDetailsFragment()
            val args = Bundle()
            args.putString(QUESTION_ID, questionId)
            fragment.arguments = args
            return fragment
        }
    }
}