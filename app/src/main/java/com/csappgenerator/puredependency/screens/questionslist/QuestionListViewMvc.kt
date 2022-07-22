package com.csappgenerator.puredependency.screens.questionslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.csappgenerator.puredependency.R
import com.csappgenerator.puredependency.questions.Question
import com.csappgenerator.puredependency.screens.common.viewsmvc.BaseViewMvc
import java.util.ArrayList

class QuestionListViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    @LayoutRes layoutId: Int = R.layout.layout_questions_list
) : BaseViewMvc<QuestionListViewMvc.Listener>(layoutInflater, parent, layoutId) {

    private val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
    private val recyclerView: RecyclerView = findViewById(R.id.recycler)
    private val questionsAdapter: QuestionsAdapter

    interface Listener {
        fun onRefreshClicked()
        fun onQuestionClicked(id: String)
    }

    init {
        // init pull-down-to-refresh
        swipeRefresh.setOnRefreshListener {
            for(listener in listeners) {
                listener.onRefreshClicked()
            }
        }

        // init recycler view
        recyclerView.layoutManager = LinearLayoutManager(context)
        questionsAdapter = QuestionsAdapter { clickedQuestion ->
            for (listener in listeners) {
                listener.onQuestionClicked(clickedQuestion.id)
            }
        }
        recyclerView.adapter = questionsAdapter
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }

    class QuestionsAdapter(
        private val onQuestionClickListener: (Question) -> Unit
    ) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

        private var questionsList: List<Question> = ArrayList(0)

        inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.txt_title)
        }

        fun bindData(questions: List<Question>) {
            questionsList = ArrayList(questions)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_question_list_item, parent, false)
            return QuestionViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.title.text = questionsList[position].title
            holder.itemView.setOnClickListener {
                onQuestionClickListener.invoke(questionsList[position])
            }
        }

        override fun getItemCount(): Int {
            return questionsList.size
        }

    }
}