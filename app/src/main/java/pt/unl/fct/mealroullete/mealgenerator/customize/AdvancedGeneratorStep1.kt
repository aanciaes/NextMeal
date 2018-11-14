package pt.unl.fct.mealroullete.mealgenerator.customize

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import pt.unl.fct.mealroullete.R

class AdvancedGeneratorStep1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_generator_step1)
        val ingredients = findViewById<ListView>(R.id.list_ingredients)

        val ing1 = IngredientRow(Ingredient(1, "Carne", "url"))

        val ing2 = IngredientRow(Ingredient(1, "Carne", "url"), Ingredient(1, "peixe", "url"))

        val arrayList = ArrayList<IngredientRow>().apply {
            add(ing1)
            add(ing2)
            add(ing1)
            add(ing2)
            add(ing1)
            add(ing2)
        }

        val adapter = IngredientListAdaptor(this, R.layout.list_ingredients, arrayList)
        ingredients.adapter = adapter
    }
}
