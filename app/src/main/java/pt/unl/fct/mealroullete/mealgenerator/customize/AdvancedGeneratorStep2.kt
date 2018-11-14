package pt.unl.fct.mealroullete.mealgenerator.customize

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import pt.unl.fct.mealroullete.R

class AdvancedGeneratorStep2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_generator_step2)
        val ingredients = findViewById<ListView>(R.id.list_ingredients)

        val ing1 = IngredientRow(1, "Carne", "url", "peixe", "sopa", "atum")
        val ing2 = IngredientRow(1, "Carne", "url", "Queijo", "Facas", "Focas")

        val arrayList = ArrayList<IngredientRow>().apply {
            add(ing1)
            add(ing2)

        }

        val adapter = IngredientListAdaptor(this, R.layout.list_ingredients, arrayList)
        ingredients.adapter = adapter

        val back = findViewById<ImageButton>(R.id.backToStep1)
        back.setOnClickListener {
            startActivity(Intent(this, AdvancedGeneratorStep1::class.java))
        }

        val generate = findViewById<Button>(R.id.buttonGenerate)
        generate.setOnClickListener {
            startActivity(Intent(this, RecipePresentation::class.java))
        }

    }
}
