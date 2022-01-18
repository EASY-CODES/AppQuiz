package jota.kalebe.quiz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import jota.kalebe.quiz.R
import jota.kalebe.quiz.model.Quiz

class QuizAdapter(var mListQuiz: List<Quiz>, var mContext: Context): PagerAdapter() {

    override fun getCount(): Int = mListQuiz.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var layoutScreen: View = inflater.inflate(R.layout.layout_quiz, null)

        var question: TextView = layoutScreen.findViewById(R.id.question)
        var item1: TextView = layoutScreen.findViewById(R.id.item1)
        var item2: TextView = layoutScreen.findViewById(R.id.item2)
        var item3: TextView = layoutScreen.findViewById(R.id.item3)
        var item4: TextView = layoutScreen.findViewById(R.id.item4)

        question.text = mListQuiz.get(position).question
        item1.text = mListQuiz.get(position).question
        item2.text = mListQuiz.get(position).incorrect_answers
        item3.text = mListQuiz.get(position).incorrect_answers
        item4.text = mListQuiz.get(position).incorrect_answers


        container.addView(layoutScreen)

        return layoutScreen
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}