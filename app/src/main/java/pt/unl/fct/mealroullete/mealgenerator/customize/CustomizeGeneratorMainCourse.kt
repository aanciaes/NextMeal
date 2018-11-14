package pt.unl.fct.mealroullete.mealgenerator.customize

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.mealgenerator.GeneratorHome

class CustomizeGeneratorMainCourse : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customize_generator_main_course)

        setListeners()
        init()
    }

    private fun setListeners () {
        val advancedGeneratorStep2 = findViewById<Button>(R.id.buttonNext)
        advancedGeneratorStep2.setOnClickListener {
            startActivity(Intent(this, CustomizeGeneratorSides::class.java))
        }

        val back = findViewById<ImageButton>(R.id.backToGeneratorHome)
        back.setOnClickListener {
            startActivity(Intent(this, GeneratorHome::class.java))
        }
    }

    val items = listOf("Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva")


    private fun init() {

        val tl = findViewById<TableLayout>(R.id.ingredient_table)

        var index = 0
        while (index < items.size) {
            val row = TableRow(this)
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
            row.layoutParams = lp

            val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var aux = 0
            while (index < items.size && aux < 4) {
                val childLayout = inflater.inflate(R.layout.table_item_ingredient, row, false) as LinearLayout

                childLayout.id = View.generateViewId()
                childLayout.findViewById<TextView>(R.id.ingredient_name).text = items[index]

                index++
                aux++
                row.addView(childLayout)
            }
            tl.addView(row)
        }
    }
}