package pt.unl.fct.mealroullete.homepage.history

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_calculator.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard
import pt.unl.fct.mealroullete.persistance.MockDatabase

class DailyHistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_daily_history, container, false)
        val container = view.findViewById<TableLayout>(R.id.ingredient_table)

        val child = inflater.inflate(R.layout.table_item_history, container, false) as LinearLayout
        val recipe = MockDatabase.getRecipe(0)
        val ingredientList = recipe.ingredients

        var totalProteins = 0
        var totalFats = 0
        var totalCarbs = 0
        var totalCalories = 0

        for (ingredient in ingredientList) {
            totalProteins += ingredient.protein
            totalFats += ingredient.fats
            totalCarbs += ingredient.carbs
            totalCalories += ingredient.calories
        }

        child.findViewById<ImageView>(R.id.historyImage).setImageResource((recipe.image as Int))
        child.findViewById<TextView>(R.id.historyName).text = recipe.name
        child.findViewById<TextView>(R.id.historyCalories).text = totalCalories.toString() + " kcal"

        view.findViewById<TextView>(R.id.history_total_calories).text = totalCalories.toString() + " kcal"
        view.findViewById<TextView>(R.id.history_total_carbs).text = totalCarbs.toString() + " g"
        view.findViewById<TextView>(R.id.history_total_proteins).text = totalProteins.toString() + " g"
        view.findViewById<TextView>(R.id.history_total_fats).text = totalFats.toString() + " g"

        child.setOnClickListener {
            val intent = Intent(context, RecipeCard::class.java)
            val b = Bundle()
            b.putString("name", recipe.name) //Your id
            intent.putExtras(b)
            startActivity(intent)
        }
        container.addView(child)

        return view
    }
}
