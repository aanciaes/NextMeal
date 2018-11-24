package pt.unl.fct.mealroullete.homepage.recipe

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.mealgenerator.GeneratorHome

class FavoriteRecipeFragment : Fragment() {

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_recipe, container, false)
        val container = view.findViewById<TableLayout>(R.id.ingredient_table)

        var x = 10
        while(x > 0){

            val row = TableRow(this.context)
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
            row.layoutParams = lp
            val child = inflater.inflate(R.layout.table_item_favorite, row, false) as LinearLayout
            child.setOnClickListener {
                startActivity(Intent(this.context, RecipeCard::class.java))
            }
            child.findViewById<TextView>(R.id.favoriteName).text = "Recipe Name" + x
            val removeFavorite = child.findViewById<ImageButton>(R.id.removeFavorite)

            removeFavorite.setOnClickListener {
                val builder = AlertDialog.Builder(this.context)

                val text = TextView(this.context)
                text.text = "Do you want to remove this recipe from your favorites?"
                text.setTextColor(R.color.colorAccent)
                text.setPadding(40, 60, 40, 60)
                builder.setView(text)
                builder.setPositiveButton("Yes") { _, _ ->
                    val linearToRemove = it.parent as LinearLayout
                    val rowToRemove = linearToRemove.parent as TableRow
                    val parentTable = rowToRemove.parent as TableLayout
                    parentTable.removeView(rowToRemove)
                }

                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
                builder.show()
            }

            row.addView(child)
            container.addView(row)
            x--
        }



        return view
    }
}
