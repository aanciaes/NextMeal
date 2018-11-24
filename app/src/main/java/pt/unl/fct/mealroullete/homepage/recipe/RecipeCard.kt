package pt.unl.fct.mealroullete.homepage.recipe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.mealgenerator.GeneratorHome

class RecipeCard : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_recipe)

        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }


}
