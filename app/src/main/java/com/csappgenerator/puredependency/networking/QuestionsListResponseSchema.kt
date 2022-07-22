package com.csappgenerator.puredependency.networking

import com.google.gson.annotations.SerializedName
import com.csappgenerator.puredependency.questions.Question

class QuestionsListResponseSchema(@SerializedName("items") val questions: List<Question>)