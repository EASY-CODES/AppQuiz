package jota.kalebe.quiz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.view.get
import jota.kalebe.quiz.R
import jota.kalebe.quiz.model.QuizHttp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val CATEGORY = "CATEGORY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tableLayout: TableLayout = findViewById(R.id.tablelayout)

        loadItens(tableLayout)
        openActivity(tableLayout, R.id.category1, QuizHttp.MATHEMATICS)
        openActivity(tableLayout, R.id.category2, QuizHttp.MUSIC)
        openActivity(tableLayout, R.id.category3, QuizHttp.FILM)
        openActivity(tableLayout, R.id.category4, QuizHttp.SCIENCENATURE)
        openActivity(tableLayout, R.id.category5, QuizHttp.GEOGRAFY)
        openActivity(tableLayout, R.id.category6, QuizHttp.HISTORY)


    }

    fun openActivity(tableLayout: TableLayout , position: Int, category: String){
        val row: View = tableLayout.findViewById(position)
        row.setOnClickListener {
            intent = Intent(this, QuizActivity::class.java)
            intent.putExtra(CATEGORY, category)
            this.startActivity(intent)
        }
    }

    fun loadItens(table: TableLayout){
        table.findViewById<View>(R.id.category1).findViewById<ImageView>(R.id.imgCover).setImageResource(R.drawable.icon_math)
        table.findViewById<View>(R.id.category1).findViewById<TextView>(R.id.category).text="Mathematics"

        table.findViewById<View>(R.id.category2).findViewById<ImageView>(R.id.imgCover).setImageResource(R.drawable.icon_music)
        table.findViewById<View>(R.id.category2).findViewById<TextView>(R.id.category).text="Music"

        table.findViewById<View>(R.id.category3).findViewById<ImageView>(R.id.imgCover).setImageResource(R.drawable.icon_movie)
        table.findViewById<View>(R.id.category3).findViewById<TextView>(R.id.category).text="Film"

        table.findViewById<View>(R.id.category4).findViewById<ImageView>(R.id.imgCover).setImageResource(R.drawable.icon_science_nature)
        table.findViewById<View>(R.id.category4).findViewById<TextView>(R.id.category).text="Science&Nature"

        table.findViewById<View>(R.id.category5).findViewById<ImageView>(R.id.imgCover).setImageResource(R.drawable.icon_world)
        table.findViewById<View>(R.id.category5).findViewById<TextView>(R.id.category).text="Geography"

        table.findViewById<View>(R.id.category6).findViewById<ImageView>(R.id.imgCover).setImageResource(R.drawable.icon_history)
        table.findViewById<View>(R.id.category6).findViewById<TextView>(R.id.category).text="History"
    }

}