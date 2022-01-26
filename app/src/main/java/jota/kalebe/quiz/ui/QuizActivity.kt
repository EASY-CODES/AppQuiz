package jota.kalebe.quiz.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import jota.kalebe.quiz.R
import jota.kalebe.quiz.ui.adapter.QuizAdapter
import jota.kalebe.quiz.ui.fragment.GenerateListsOfFragments
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.layout_quiz.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.http2.Header

class QuizActivity : AppCompatActivity() {
    private val CATEGORY = "CATEGORY"

    lateinit var btnNext: Button;
    lateinit var btnQuit: Button;
    lateinit var txtTitleQuestion: TextView;
    lateinit var textTitle: TextView
    var position = 0;

    var handler = Handler();


    val viewModel: QuizListViewmodel by lazy {
        ViewModelProvider(this).get(QuizListViewmodel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        txtTitleQuestion = findViewById(R.id.questiontitle)
        btnNext = findViewById(R.id.btNext)
        btnQuit = findViewById(R.id.btQuit)
        textTitle = findViewById(R.id.titleCategory)


        //set text Title
        val category = intent.getStringExtra(CATEGORY)
        val newIntent = Intent(this, Result::class.java)

        if (category != null) {
            viewModel.state.observe(this, Observer { state ->
                when (state) {
                    is QuizListViewmodel.State.Loading -> {
                        vwLoading.visibility = View.VISIBLE
                    }
                    is QuizListViewmodel.State.Loaded -> {
                        vwLoading.visibility = View.GONE

                        runOnUiThread(Runnable { txtTitleQuestion.text = "Question ${viewpager.currentItem+1}/10" })
                        textTitle.visibility = View.VISIBLE
                        txtTitleQuestion.visibility = View.VISIBLE
                        btnNext.visibility = View.VISIBLE
                        btnQuit.visibility = View.VISIBLE

                        textTitle.text = state.items[0].category

                        viewpager.adapter =
                            QuizAdapter(this, GenerateListsOfFragments(state.items).generate());

                        TabLayoutMediator(tabLayout, viewpager) { tab, position ->

                        }.attach()
                    }
                    is QuizListViewmodel.State.Error -> {
                        vwLoading.visibility = View.GONE
                        if (!state.hasConsumed) {
                            state.hasConsumed = true
                            Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })

            viewModel.loadQuestions(category)
        }

        btnQuit.setOnClickListener() {
            finish()
        }





        viewModel.answer.observe(this, Observer {
            answer ->

            println("answer: $answer")

            position = viewpager.currentItem

            btnNext.setOnClickListener(){
                if (btnNext.text == "Submit"){

                    if (answer.user_answers.text == answer.correct_answer.text){
                        answer.correct_answer.setBackgroundResource(R.drawable.bg_alternative_correct)
                        viewModel.setScore()
                    }else{
                        answer.user_answers.setBackgroundResource(R.drawable.bg_alternative_incorrect)
                        answer.correct_answer.setBackgroundResource(R.drawable.bg_alternative_correct)
                    }

                    btnNext.text = "Next"
                }
                else {

                    if (position < 10) {
                        position = position + 1
                        viewpager.currentItem = position;
                        txtTitleQuestion.text = "Question ${viewpager.currentItem+1}/10"
                        btnNext.text = "Submit"
                    }

                    if (position == 10) {
                        newIntent.putExtra("score", viewModel.score.value)
                        startActivity(newIntent)
                        finish()
                    }
                }
            }
        })

    }
}
