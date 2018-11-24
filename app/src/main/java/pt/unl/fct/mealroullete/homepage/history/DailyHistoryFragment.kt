package pt.unl.fct.mealroullete.homepage.history

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard

class DailyHistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_daily_history, container, false)
        val container = view.findViewById<TableLayout>(R.id.ingredient_table)

        var x = 1
        while(x < 4){
            val child = inflater.inflate(R.layout.table_item_history, container, false) as LinearLayout
            child.findViewById<TextView>(R.id.historyName).text = "Dish Name" + x
            child.setOnClickListener {
                startActivity(Intent(context, RecipeCard::class.java))
            }
            container.addView(child)
            x++
        }
        return view
    }
}
