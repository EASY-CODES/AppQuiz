package jota.kalebe.quiz.model

import retrofit2.http.GET
import retrofit2.http.Query

interface QuizHttp {


    @GET("api.php?")
    suspend fun getQuestions(
        @Query("amount") amount: String,
        @Query("category") category: String,
        @Query("difficulty") difficult: String,
        @Query("type") type: String
    ): SearchQuiz


}