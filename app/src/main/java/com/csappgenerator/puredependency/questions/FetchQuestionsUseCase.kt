package com.csappgenerator.puredependency.questions

import android.util.Log
import com.csappgenerator.puredependency.networking.StackoverflowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class FetchQuestionsUseCase(private val stackoverflowApi: StackoverflowApi) {

    sealed class Result {
        data class Success(val questions: List<Question>) : Result()
        object Error : Result()
    }

    suspend fun fetchQuestions(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!.questions)
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())

                    Log.d("myerror", jObjError.getJSONObject("error").getString("message"))
                    return@withContext Result.Error
                }
            } catch (t: Throwable) {
                Log.d("myerror", t.message.toString())
                return@withContext Result.Error
            }
        }
    }
}