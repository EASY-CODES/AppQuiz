package jota.kalebe.quiz.network

import jota.kalebe.quiz.model.QuizHttp
import javax.inject.Inject

class QuizService
@Inject
constructor(private val api: QuizHttp) {

    suspend fun getAllQuestions(
        amount: String,
        category: String,
        difficult: String,
        type: String
    ) =
        api.getQuestions(amount, category, difficult, type)

}