package com.csappgenerator.puredependency.common.di

import com.csappgenerator.puredependency.questions.FetchQuestionDetailsUseCase
import com.csappgenerator.puredependency.questions.FetchQuestionsUseCase
import com.csappgenerator.puredependency.screens.common.ScreensNavigator
import com.csappgenerator.puredependency.screens.common.dialogs.DialogNavigator
import com.csappgenerator.puredependency.screens.common.viewsmvc.ViewMvcFactory
import java.lang.Exception
import java.lang.reflect.Field

class Injector(private val presentationCompositionRoot: PresentationCompositionRoot) {
    fun inject(client: Any) {
        for (field in getAllFields(client)) {
            if (isAnnotatedForInjection(field)) {
                injectField(client, field)
            }
        }
    }

    private fun getAllFields(client: Any): Array<out Field> {
        val clientClass = client::class.java
        return clientClass.declaredFields
    }

    private fun isAnnotatedForInjection(field: Field): Boolean {
        val fieldAnnotations = field.annotations
        for (annotation in fieldAnnotations) {
            if (annotation is Service) {
                return true
            }
        }
        return false
    }

    private fun injectField(client: Any, field: Field) {
        val isAccessibleInitially = field.isAccessible
        field.isAccessible = true
        field.set(client, getServiceForClass(field.type))
        field.isAccessible = isAccessibleInitially
    }

    private fun getServiceForClass(type: Class<*>): Any {
        when (type) {
            DialogNavigator::class.java -> {
                return presentationCompositionRoot.dialogNavigator
            }
            ScreensNavigator::class.java -> {
                return presentationCompositionRoot.screensNavigator
            }
            FetchQuestionsUseCase::class.java -> {
                return presentationCompositionRoot.fetchQuestionsUseCase
            }
            FetchQuestionDetailsUseCase::class.java -> {
                return presentationCompositionRoot.fetchQuestionDetailsUseCase
            }
            ViewMvcFactory::class.java -> {
                return presentationCompositionRoot.viewMvcFactory
            }
            else -> {
                throw Exception("unsupported service type: $type")
            }
        }
    }
}