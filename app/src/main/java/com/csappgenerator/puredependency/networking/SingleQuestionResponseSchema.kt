package com.csappgenerator.puredependency.networking

import com.google.gson.annotations.SerializedName
import com.csappgenerator.puredependency.questions.QuestionWithBody

data class SingleQuestionResponseSchema(@SerializedName("items") val questions: List<QuestionWithBody>) {
    val question: QuestionWithBody get() = questions[0]
}