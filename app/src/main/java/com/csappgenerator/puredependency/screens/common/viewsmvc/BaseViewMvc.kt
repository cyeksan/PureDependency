package com.csappgenerator.puredependency.screens.common.viewsmvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes


open class BaseViewMvc<T>(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    layoutId: Int
) {
    protected val listeners = arrayListOf<T>()
    val root: View = layoutInflater.inflate(layoutId, parent, false)
    protected val context: Context get() = root.context

    fun registerListener(listener: T) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: T) {
        listeners.remove(listener)
    }

    protected fun <T : View?> findViewById(@IdRes id: Int): T {
        return root.findViewById<T>(id)
    }
}