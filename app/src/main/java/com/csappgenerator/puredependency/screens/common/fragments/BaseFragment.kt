package com.csappgenerator.puredependency.screens.common.fragments

import androidx.fragment.app.Fragment
import com.csappgenerator.puredependency.common.di.Injector
import com.csappgenerator.puredependency.common.di.PresentationCompositionRoot
import com.csappgenerator.puredependency.screens.common.activities.BaseActivity

open class BaseFragment : Fragment() {

    private val presentationCompositionRoot: PresentationCompositionRoot by lazy {
        PresentationCompositionRoot((requireActivity() as BaseActivity).activityCompositionRoot)
    }
    val injector: Injector get() = Injector(presentationCompositionRoot)
}