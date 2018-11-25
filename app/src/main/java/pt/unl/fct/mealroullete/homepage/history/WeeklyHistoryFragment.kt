package pt.unl.fct.mealroullete.homepage.history

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard
import pt.unl.fct.mealroullete.persistance.MockDatabase

class WeeklyHistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weekly_history, container, false)
        val container = view.findViewById<TableLayout>(R.id.ingredient_table)

        var totalProteins = 0
        var totalFats = 0
        var totalCarbs = 0
        var totalCalories = 0

        var x = 0
        while(x < 3) {
            val child = inflater.inflate(R.layout.table_item_history, container, false) as LinearLayout
            val recipe = MockDatabase.getRecipe(x)
            val ingredientList = recipe.ingredients

            for (ingredient in ingredientList) {
                totalProteins += ingredient.protein
                totalFats += ingredient.fats
                totalCarbs += ingredient.carbs
                totalCalories += ingredient.calories
            }

            child.findViewById<ImageView>(R.id.historyImage).setImageResource((recipe.image as Int))
            child.findViewById<TextView>(R.id.historyName).text = recipe.name
            child.findViewById<TextView>(R.id.historyCalories).text = recipe.calories.toString()
            child.setOnClickListener {
                val intent = Intent(context, RecipeCard::class.java)
                val b = Bundle()
                b.putString("name", recipe.name) //Your id
                intent.putExtras(b)
                startActivity(intent)
            }
            container.addView(child)
            x++
        }

        return view
    }
}
