package jota.kalebe.quiz.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jota.kalebe.quiz.R
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel
import kotlinx.android.synthetic.main.activity_result.*

class Result : AppCompatActivity() {
    lateinit var btShare: Button;
    lateinit var btNewQuiz: Button;

    val viewModel: QuizListViewmodel by lazy {
        ViewModelProvider(this).get(QuizListViewmodel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        btNewQuiz = findViewById(R.id.btnNewQuiz)
        btShare = findViewById(R.id.btnShareResult)

        btNewQuiz.setOnClickListener() {
            finish()
        }
        val hits = intent.getIntExtra("score", 0)

//        val hits = list?.filter { q-> q.answer }?.size
//
//        println("hits $hits")

        questions_results.text = "$hits / 10"
        coins.text = "${hits!! * 5}"
        //mostrar resultados na tela



    }
}