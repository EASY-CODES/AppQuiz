package jota.kalebe.quiz.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jota.kalebe.quiz.ui.fragment.GenerateListsOfFragments
import jota.kalebe.quiz.ui.fragment.QuizFragment

class QuizAdapter(fa: FragmentActivity, listFragment: List<QuizFragment>) : FragmentStateAdapter(fa) {

    private val listFragment = listFragment;



    override fun getItemCount(): Int = this.listFragment.size

    override fun createFragment(position: Int): Fragment {
        return this.listFragment[position];
    }

}