package jota.kalebe.quiz.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import jota.kalebe.quiz.R
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class QuizAdapter(var mListQuiz: List<Quiz>, var mContext: Context) : PagerAdapter() {


    var isSelected = false
    override fun getCount(): Int = mListQuiz.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var inflater: LayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var layoutScreen: View = inflater.inflate(R.layout.layout_quiz, null)

        var question: TextView = layoutScreen.findViewById(R.id.question)
        var item1: TextView = layoutScreen.findViewById(R.id.item1)
        var item2: TextView = layoutScreen.findViewById(R.id.item2)
        var item3: TextView = layoutScreen.findViewById(R.id.item3)
        var item4: TextView = layoutScreen.findViewById(R.id.item4)

        val quiz = mListQuiz.get(position)

        question.text = quiz.question

        if (quiz.type == "boolean") {
            item1.text = quiz.correct_answer
            item2.text = quiz.incorrect_answers[0]
            item3.visibility = View.GONE
            item4.visibility = View.GONE
        } else {

            val strings = scrambleString(quiz.correct_answer,
                quiz.incorrect_answers[0],
                quiz.incorrect_answers[1],
                quiz.incorrect_answers[2])
            item1.text = strings[0]
            item2.text = strings[1]
            item3.text = strings[2]
            item4.text = strings[3]
        }

        item1.setOnClickListener(){

        }
        item2.setOnClickListener(){

        }
        item3.setOnClickListener(){

        }
        item4.setOnClickListener(){

        }


        container.addView(layoutScreen)

        return layoutScreen
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
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