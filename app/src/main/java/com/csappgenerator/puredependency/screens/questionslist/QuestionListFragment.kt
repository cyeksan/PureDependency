package com.csappgenerator.puredependency.screens.questionslist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.csappgenerator.puredependency.common.di.Service
import com.csappgenerator.puredependency.questions.FetchQuestionsUseCase
import com.csappgenerator.puredependency.screens.common.ScreensNavigator
import com.csappgenerator.puredependency.screens.common.dialogs.DialogNavigator
import com.csappgenerator.puredependency.screens.common.fragments.BaseFragment
import com.csappgenerator.puredependency.screens.common.viewsmvc.ViewMvcFactory
import kotlinx.coroutines.*


class QuestionsListFragment : BaseFragment(), QuestionListViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionListViewMvc: QuestionListViewMvc
    private var isDataLoaded = false
    @field:Service private lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase
    @field:Service private lateinit var dialogNavigator: DialogNavigator
    @field:Service private lateinit var screensNavigator: ScreensNavigator
    @field:Service private lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }

    override fun onStart() {
        super.onStart()
        questionListViewMvc.registerListener(this)
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        questionListViewMvc = viewMvcFactory.newQuestionListViewMvc(container)
        return questionListViewMvc.root
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            questionListViewMvc.showProgressIndication()
            try {
                when (val result = fetchQuestionsUseCase.fetchQuestions()) {
                    is FetchQuestionsUseCase.Result.Success -> {
                        questionListViewMvc.bindQuestions(result.questions)
                        isDataLoaded = true
                    }
                    is FetchQuestionsUseCase.Result.Error -> onFetchFailed()
                }
            } finally {
                questionListViewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogNavigator.showServerErrorDialog()
    }

    override fun onStop() {
        super.onStop()
        questionListViewMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(id: String) {
        screensNavigator.navigateToDetailScreen(id)
    }

    companion object {
        fun newInstance(): Fragment {
            return QuestionsListFragment()
        }
    }
}