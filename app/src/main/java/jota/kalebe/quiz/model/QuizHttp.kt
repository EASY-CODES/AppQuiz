package jota.kalebe.quiz.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.util.concurrent.TimeUnit

object QuizHttp {
    //category
    val MUSIC = "10"
    val FILM = "11"
    val MATHEMATICS = "19"
    val SCIENCENATURE = "17"
    val GEOGRAFY = "22"
    val HISTORY = "23"

    //limit questions
    val AMOUT = "10"

    //difficulty
    val EASY = "easy"
    val MEDIUM = "medium"
    val HARD = "hard"

    //type of questions
    val MULTIPLE = "multiple"
    val BOOLEAN = "boolean"

    val URL = "https://opentdb.com/api.php?amount=%s&category=%s&difficulty=%s&type=%s"
    val URL_MATH = "https://opentdb.com/api.php?amount=%s&category=%s&difficulty=%s"

    private val client = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    fun searchQuestions(amount:String, category: String, difficult: String, type: String): SearchQuiz? {
        if (category == MATHEMATICS){
            return searchQuestionsMath(amount, category, difficult)
        }

        val request = Request.Builder()
            .url(String.format(URL, amount, category, difficult, type))
            .build()


        try {
            val response = client.newCall(request).execute()
            val json = response.body?.string()
            return Gson().fromJson(json, SearchQuiz::class.java)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
    fun searchQuestionsMath(amount:String, category: String, difficult: String): SearchQuiz? {
        val request = Request.Builder()
            .url(String.format(URL_MATH, amount, category, difficult))
            .build()

        try {
            val response = client.newCall(request).execute()
            val json = response.body?.string()
            return Gson().fromJson(json, SearchQuiz::class.java)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}