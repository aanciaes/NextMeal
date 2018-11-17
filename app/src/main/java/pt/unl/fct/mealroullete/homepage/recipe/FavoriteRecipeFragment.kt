package pt.unl.fct.mealroullete.homepage.recipe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import pt.unl.fct.mealroullete.R

class FavoriteRecipeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_recipe, container, false)
        val container = view.findViewById<TableLayout>(R.id.ingredient_table)
        var x = 10
        while(x > 0){

            val row = TableRow(this.context)
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
            row.layoutParams = lp
            val child = inflater.inflate(R.layout.table_item_favorite, row, false) as LinearLayout
            row.addView(child)
            container.addView(row)
            x--
        }

        return view
    }
}
