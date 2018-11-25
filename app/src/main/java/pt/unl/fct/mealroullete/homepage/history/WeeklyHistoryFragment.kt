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
            child.findViewById<TextView>(R.id.historyCalories).text = totalCalories.toString() + " kcal"

            val caloriesOldValue = view.findViewById<TextView>(R.id.history_total_calories).text.toString().split(" ")[0].toInt()
            val caloriesValue = caloriesOldValue + totalCalories
            view.findViewById<TextView>(R.id.history_total_calories).text = caloriesValue.toString() + " kcal"

            val proteinOldValue = view.findViewById<TextView>(R.id.history_total_proteins).text.toString().split(" ")[0].toInt()
            val proteinValue = proteinOldValue + totalProteins
            view.findViewById<TextView>(R.id.history_total_proteins).text = proteinValue.toString() + " g"

            val fatsOldValue = view.findViewById<TextView>(R.id.history_total_fats).text.toString().split(" ")[0].toInt()
            val fatsValue = fatsOldValue + totalFats
            view.findViewById<TextView>(R.id.history_total_fats)?.text = fatsValue.toString() + " g"

            val carbsOldValue = view.findViewById<TextView>(R.id.history_total_carbs).text.toString().split(" ")[0].toInt()
            val carbsValue = carbsOldValue + totalCarbs
            view.findViewById<TextView>(R.id.history_total_carbs)?.text = carbsValue.toString() + " g"


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
