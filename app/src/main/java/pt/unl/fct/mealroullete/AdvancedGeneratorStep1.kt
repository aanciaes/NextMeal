package pt.unl.fct.mealroullete

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class AdvancedGeneratorStep1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_generator_step1)
        val ingredients = findViewById<ListView>(R.id.list_ingredients)

        val ing1 = IngredientRow(1,"Carne","url", "peixe", "sopa", "atum")
        val ing2 = IngredientRow(1,"Carne","url", "Queijo", "Facas", "Focas")

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
