package com.csappgenerator.puredependency.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.csappgenerator.puredependency.R
import com.csappgenerator.puredependency.screens.common.toolbar.MyToolbar
import com.csappgenerator.puredependency.screens.common.viewsmvc.BaseViewMvc

class QuestionDetailsViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    @LayoutRes layoutId: Int = R.layout.layout_question_details
) : BaseViewMvc<QuestionDetailsViewMvc.Listener>(layoutInflater, parent, layoutId) {

    private var toolbar: MyToolbar = findViewById(R.id.toolbar)
    private var swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
    private var txtQuestionBody: TextView = findViewById(R.id.txt_question_body)

    interface Listener {
        fun onBackClicked()
    }

    init {
        // init toolbar
        toolbar.setNavigateUpListener {
            for(listener in listeners) {
                listener.onBackClicked()
            }
        }
        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh.isEnabled = false
    }

    fun bindQuestionBody(questionBody: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtQuestionBody.text = Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            txtQuestionBody.text = Html.fromHtml(questionBody)
        }
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }

}