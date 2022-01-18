package jota.kalebe.quiz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
import jota.kalebe.quiz.R
import jota.kalebe.quiz.ui.adapter.QuizAdapter
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.coroutines.launch
import jota.kalebe.quiz.model.Quiz as Quiz

class QuizActivity : AppCompatActivity() {
    private val CATEGORY = "CATEGORY"

    lateinit var screenPager: ViewPager;
    lateinit var quizPagerAdaper: QuizAdapter;
    lateinit var tabIndicator: TabLayout;
    lateinit var btnNext: Button;
    lateinit var txtTitleQuestion: TextView
    var position = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)



        val categoryTitle = intent.getStringExtra(CATEGORY)

        val textTitle: TextView = findViewById(R.id.titleCategory)
        textTitle.text = "$categoryTitle Quiz"


        var list = listOf(
            Quiz("Essa é a questão 01", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 02", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 03", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 04", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 05", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 06", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 07", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 08", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 09", "ckdncjwkn", "wdaclkdwnc"),
            Quiz("Essa é a questão 10", "ckdncjwkn", "wdaclkdwnc")
        )

        screenPager = findViewById(R.id.screen_viewpager)
        txtTitleQuestion = findViewById(R.id.questiontitle)
        tabIndicator = findViewById(R.id.tabLayout)
        btnNext = findViewById(R.id.btNext)

        quizPagerAdaper = QuizAdapter(list, this)
        screenPager.adapter = quizPagerAdaper

        //setup layout


        tabIndicator.setupWithViewPager(screenPager)

        btnNext.setOnClickListener(){
            position = screenPager.currentItem
            if (position < list.size) {
                position = position + 1
                screenPager.setCurrentItem(position)
            }

            if(position == list.size - 1){
                btnNext.text="Finalizar"
            }

            if(position == list.size){
                intent = Intent(this, Result::class.java)
                this.startActivity(intent)
                finish()
            }
        }
        }
    }
