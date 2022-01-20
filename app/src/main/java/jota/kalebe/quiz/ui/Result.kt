package jota.kalebe.quiz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import jota.kalebe.quiz.R

class Result : AppCompatActivity() {
    lateinit var btShare: Button;
    lateinit var btNewQuiz: Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        btNewQuiz = findViewById(R.id.btnNewQuiz)
        btShare = findViewById(R.id.btnShareResult)

        btNewQuiz.setOnClickListener(){
            finish()
        }

    }
}