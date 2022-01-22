package jota.kalebe.quiz.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import jota.kalebe.quiz.R
import jota.kalebe.quiz.model.Quiz

class QuizFragment(quiz: Quiz): Fragment(){

    private val quiz = quiz;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_quiz, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var question: TextView = view.findViewById(R.id.question)
        var item1: TextView = view.findViewById(R.id.item1)
        var item2: TextView = view.findViewById(R.id.item2)
        var item3: TextView = view.findViewById(R.id.item3)
        var item4: TextView = view.findViewById(R.id.item4)



        question.text = this.quiz.question

        if (this.quiz.type == "boolean") {
            item1.text = this.quiz.correct_answer
            item2.text = this.quiz.incorrect_answers[0]
            item3.visibility = View.GONE
            item4.visibility = View.GONE
        } else {

            val strings = scrambleString(this.quiz.correct_answer,
                this.quiz.incorrect_answers[0],
                this.quiz.incorrect_answers[1],
                this.quiz.incorrect_answers[2])
            item1.text = strings[0]
            item2.text = strings[1]
            item3.text = strings[2]
            item4.text = strings[3]
        }



    }

    fun scrambleString(s1: String, s2: String, s3: String, s4: String): ArrayList<String> {
        var sRandom = arrayListOf<String>(s1, s2, s3, s4)
        var string: String
        var strings = arrayListOf<String>()
        while (strings.size < 4) {
            string = sRandom.random()
            if (!strings.contains(string))
                strings.add(string)
        }
        return strings
    }

}