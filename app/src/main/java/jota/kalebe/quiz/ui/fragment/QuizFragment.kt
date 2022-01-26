package jota.kalebe.quiz.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jota.kalebe.quiz.R
import jota.kalebe.quiz.model.Answer
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.layout_quiz.*
import kotlinx.coroutines.delay

class QuizFragment(quiz: Quiz): Fragment(), View.OnClickListener{

    private val quiz = quiz;
    private var answer: Answer? = null;
    private var textSelected:TextView? = null;

    val viewModel: QuizListViewmodel by lazy {
        ViewModelProvider(requireActivity()).get(QuizListViewmodel::class.java)
    }

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

            item1.setOnClickListener(this)
            item2.setOnClickListener(this)
        } else {

            val strings = scrambleString(this.quiz.correct_answer,
                this.quiz.incorrect_answers[0],
                this.quiz.incorrect_answers[1],
                this.quiz.incorrect_answers[2])

            item1.text = strings[0]
            item2.text = strings[1]
            item3.text = strings[2]
            item4.text = strings[3]

            item1.setOnClickListener(this)
            item2.setOnClickListener(this)
            item3.setOnClickListener(this)
            item4.setOnClickListener(this)
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
        return strings;
    }

    override fun onClick(text: View?) {
        var correctAnswer: View? = null

        if(textSelected != null)
            textSelected?.setBackgroundResource(R.drawable.bg_alternative)

        text?.setBackgroundResource(R.drawable.bg_alternative_selected)
        textSelected = text as TextView

        if(item1.text == quiz.correct_answer)
            correctAnswer = item1
        if(item2.text == quiz.correct_answer)
            correctAnswer = item2
        if(item3.text == quiz.correct_answer)
            correctAnswer = item3
        if(item4.text == quiz.correct_answer)
            correctAnswer = item4

        answer = Answer(text as TextView, correctAnswer as TextView)

        viewModel.setAnswer(answer!!)


    }
}