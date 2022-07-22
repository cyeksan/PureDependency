package com.csappgenerator.puredependency.screens.common.activities

import android.os.Bundle
import com.csappgenerator.puredependency.R
import com.csappgenerator.puredependency.screens.questionslist.QuestionsListFragment


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.frame_content, QuestionsListFragment()
            ).commit()
        }
    }
}