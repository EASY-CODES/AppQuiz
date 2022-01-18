package jota.kalebe.quiz.model

data class Quiz(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: String
)