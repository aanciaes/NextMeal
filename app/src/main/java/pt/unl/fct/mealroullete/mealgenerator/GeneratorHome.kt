package pt.unl.fct.mealroullete.mealgenerator

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard
import pt.unl.fct.mealroullete.mealgenerator.customize.CustomizeGeneratorMainCourse
import pt.unl.fct.mealroullete.mealgenerator.customize.RecipePresentation
import pt.unl.fct.mealroullete.persistance.MockDatabase
import java.io.Serializable

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

        val randomGenerator = findViewById<Button>(R.id.random)
        randomGenerator.setOnClickListener {
            val intent = Intent(this, RecipePresentation::class.java)
            //intent.putExtra("recipeList", MockDatabase.recipesList.map { "${it.id};${it.name}" })
            startActivity(intent)
        }

        val topRecipe1 = findViewById<ImageButton>(R.id.imageView)
        topRecipe1.setOnClickListener {
            val intent = Intent(this, RecipeCard::class.java)
            val b = Bundle()
            b.putString("name", "Russian Cabbage Rolls with Gravy") //Your id
            intent.putExtras(b)
            startActivity(intent)
        }

        val topRecipe2 = findViewById<ImageButton>(R.id.imageView2)
        topRecipe2.setOnClickListener {
            val intent = Intent(this, RecipeCard::class.java)
            val b = Bundle()
            b.putString("name", "Marinated Tuna Steak") //Your id
            intent.putExtras(b)
            startActivity(intent)
        }

        val topRecipe3 = findViewById<ImageButton>(R.id.imageView3)
        topRecipe3.setOnClickListener {
            val intent = Intent(this, RecipeCard::class.java)
            val b = Bundle()
            b.putString("name", "Tofu Salad") //Your id
            intent.putExtras(b)
            startActivity(intent)
        }
    }
}
