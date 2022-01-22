package jota.kalebe.quiz.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import jota.kalebe.quiz.R
import jota.kalebe.quiz.model.Answer
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.ui.adapter.QuizAdapter
import jota.kalebe.quiz.ui.fragment.GenerateListsOfFragments
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {
    private val CATEGORY = "CATEGORY"

    lateinit var btnNext: Button;
    lateinit var btnQuit: Button;
    lateinit var txtTitleQuestion: TextView;
    lateinit var textTitle: TextView
    var position = 0;


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
        intent = Intent(this, Result::class.java)

        if (category != null) {
            viewModel.state.observe(this, Observer { state ->
                when (state) {
                    is QuizListViewmodel.State.Loading -> {
                        vwLoading.visibility = View.VISIBLE
                    }
                    is QuizListViewmodel.State.Loaded -> {
                        vwLoading.visibility = View.GONE

                        viewpager.adapter =
                            QuizAdapter(this, GenerateListsOfFragments(state.items).generate());

                        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
                            textTitle.text = "Question ${position}/10"
                        }.attach()

                        btnNext.setOnClickListener() {
                            position = viewpager.currentItem
                            if (position < tabLayout.tabGravity) {
                                position = position + 1
                                viewpager.currentItem = position;
                            }

                            if (position == state.items.size) {

                                this.startActivity(intent)
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
