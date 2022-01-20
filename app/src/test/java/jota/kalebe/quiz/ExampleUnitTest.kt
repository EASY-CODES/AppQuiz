package jota.kalebe.quiz

import jota.kalebe.quiz.model.QuizHttp
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val request = QuizHttp.searchQuestions(
            QuizHttp.AMOUT,
            QuizHttp.GEOGRAFY,
            QuizHttp.EASY,
            QuizHttp.MULTIPLE
        )

        request?.results?.forEach { quiz->
            println(quiz)
        }
    }
}