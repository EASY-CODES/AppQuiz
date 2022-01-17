package jota.kalebe.quiz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import jota.kalebe.quiz.R

class Quiz : AppCompatActivity() {
    private val CATEGORY = "CATEGORY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val categoryTitle = intent.getStringExtra(CATEGORY)

        val textTitle: TextView = findViewById(R.id.titleCategory)
        textTitle.text = "$categoryTitle Quiz"

        val btNext: Button = findViewById(R.id.btNext)
        btNext.setOnClickListener {
            intent = Intent(this, Result::class.java)
            this.startActivity(intent)
        }
    }
}