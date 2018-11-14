package pt.unl.fct.mealroullete.mealgenerator

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import pt.unl.fct.mealroullete.mealgenerator.customize.CustomizeGeneratorMainCourse

class GeneratorHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generator_home)

        val back = findViewById<ImageButton>(R.id.backToHome)
        back.setOnClickListener {
            startActivity(Intent(this, RecipeActivity::class.java))
        }
        val advancedGeneratorStep1 = findViewById<Button>(R.id.customize)
        advancedGeneratorStep1.setOnClickListener {
            startActivity(Intent(this, CustomizeGeneratorMainCourse::class.java))
        }
    }
}
