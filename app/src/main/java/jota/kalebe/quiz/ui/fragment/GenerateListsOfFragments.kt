package jota.kalebe.quiz.ui.fragment

import jota.kalebe.quiz.model.Quiz

class GenerateListsOfFragments (listQuiz: List<Quiz>){
    private val listQuiz = listQuiz;

    fun generate(): List<QuizFragment>{
        var fragments = ArrayList<QuizFragment>()
        for (quiz in this.listQuiz){
            fragments.add(QuizFragment(quiz))
        }
        return fragments;
    }

}