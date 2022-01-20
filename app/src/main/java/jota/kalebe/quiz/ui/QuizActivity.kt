package jota.kalebe.quiz.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.allViews
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import jota.kalebe.quiz.R
import jota.kalebe.quiz.model.Answer
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.model.QuizHttp
import jota.kalebe.quiz.model.SearchQuiz
import jota.kalebe.quiz.ui.adapter.QuizAdapter
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.layout_quiz.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {
    private val CATEGORY = "CATEGORY"

    lateinit var screenPager: ViewPager;
    lateinit var quizPagerAdaper: QuizAdapter;
    lateinit var tabIndicator: TabLayout;
    lateinit var btnNext: Button;
    lateinit var btnQuit: Button;
    lateinit var txtTitleQuestion: TextView;
    lateinit var textTitle: TextView
    lateinit var list: List<Quiz>

    var position = 0

    lateinit var answer: Answer

    val viewModel: QuizListViewmodel by lazy {
        ViewModelProvider(this).get(QuizListViewmodel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        screenPager = findViewById(R.id.screen_viewpager)
        txtTitleQuestion = findViewById(R.id.questiontitle)
        tabIndicator = findViewById(R.id.tabLayout)
        btnNext = findViewById(R.id.btNext)
        btnQuit = findViewById(R.id.btQuit)
        textTitle = findViewById(R.id.titleCategory)

        val context = this

        //set text Title
        val category = intent.getStringExtra(CATEGORY)
        intent = Intent(this, Result::class.java)

        if (category != null) {
            viewModel.state.observe(this, Observer { state ->
                when (state) {
                    is QuizListViewmodel.State.Loading -> {
                        vwLoading.visibility = View.VISIBLE
                    }
                    is QuizListViewmodel.State.Loaded -> {
                        vwLoading.visibility = View.GONE
                        list = state.items
                        quizPagerAdaper = QuizAdapter(list, context)


                        screenPager.adapter = quizPagerAdaper


                        //setup layout
                        tabIndicator.setupWithViewPager(screenPager)


                        //set text category
                        textTitle.text = "${state.items[0].category} Quiz"
                        if (screenPager.currentItem == list.size - 1) {
                            btnNext.text = "Finalizar"
                        } else {
                            btnNext.text = "Next"
                        }


                        //click button next
                        btnNext.setOnClickListener() {

                            position = screenPager.currentItem

                            if (position < list.size) {
                                position = position + 1
                                screenPager.setCurrentItem(position)
                            }

                            if (position == list.size) {

                                context.startActivity(intent)
                                finish()
                            }
                        }
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

    }

}
