package jota.kalebe.quiz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import androidx.core.view.get
import jota.kalebe.quiz.R

class MainActivity : AppCompatActivity() {
    private val CATEGORY = "CATEGORY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tableLayout: TableLayout = findViewById(R.id.tablelayout)

        openActivity(tableLayout,0, R.id.category1, "Math")
        openActivity(tableLayout,0, R.id.category2, "Music")
        openActivity(tableLayout,0, R.id.category3, "Movie")
        openActivity(tableLayout,1, R.id.category4, "Science")
        openActivity(tableLayout,1, R.id.category5, "Language")
        openActivity(tableLayout,1, R.id.category6, "Geography")



    }

    fun openActivity(tableLayout: TableLayout , row: Int, position: Int, category: String){
        val row: View = tableLayout[row].findViewById(position)
        row.setOnClickListener {
            intent = Intent(this, QuizActivity::class.java)
            intent.putExtra(CATEGORY, category)
            this.startActivity(intent)

        }
    }
}