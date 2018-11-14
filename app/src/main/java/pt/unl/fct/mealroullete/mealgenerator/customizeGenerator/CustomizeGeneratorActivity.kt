package pt.unl.fct.mealroullete.mealgenerator.customizeGenerator

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import pt.unl.fct.mealroullete.R

class CustomizeGeneratorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)
        init()
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