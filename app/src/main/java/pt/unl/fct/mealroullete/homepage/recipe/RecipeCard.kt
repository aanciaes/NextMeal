package pt.unl.fct.mealroullete.homepage.recipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity.CENTER
import android.view.Gravity.LEFT
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.persistance.MockDatabase
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.Gravity.LEFT
import android.widget.ImageView


class RecipeCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_recipe)

        val b = intent.extras
        var value = "" // or other values
        if (b != null) {
            value = b.getString("name")
            val recipe = MockDatabase.findRecipe(value)

            val namespace = findViewById<TextView>(R.id.recipeName)
            namespace.text = recipe?.name

            val image = findViewById<RoundedImageView>(R.id.recipeImage)
            if (recipe!!.image is Int) {
                image.setImageResource((recipe.image as Int))
            } else {
                image.setImageURI(Uri.parse((recipe.image as String)))
            }

            val ingredientContainer = findViewById<LinearLayout>(R.id.ingredientContainer)
            var j = 0
            for (i in recipe.ingredients) {
                val ingredientView = TextView(ingredientContainer.context)
                val txt = recipe.quantities[j].toString() + " gr " + i.name
                ingredientView.text = txt
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(10, 35, 0, 0)
                ingredientView.layoutParams = params
                ingredientView.gravity = CENTER or LEFT
                ingredientContainer.addView(ingredientView)

                val calories = findViewById<TextView>(R.id.caloriesValue)
                val caloriesOldValue = calories?.text.toString().split(" ")[0].toInt()
                val caloriesValue = caloriesOldValue + i!!.calories
                calories?.text = caloriesValue.toString() + " kcal"

                val proteins = findViewById<TextView>(R.id.nutrientProtein)
                val proteinOldValue = proteins?.text.toString().split(" ")[0].toInt()
                val proteinValue = proteinOldValue + i!!.protein
                proteins?.text = proteinValue.toString() + " gr Proteins"

                val fats = findViewById<TextView>(R.id.nutrientFats)
                val fatsOldValue = fats?.text.toString().split(" ")[0].toInt()
                val fatsValue = fatsOldValue + i!!.fats
                fats?.text = fatsValue.toString() + " gr Fats"

                val carbs = findViewById<TextView>(R.id.nutrientCarbs)
                val carbsOldValue = carbs?.text.toString().split(" ")[0].toInt()
                val carbsValue = carbsOldValue + i!!.carbs
                carbs?.text = carbsValue.toString() + " gr Carbs"
                j++
            }
            val instructionContainer = findViewById<LinearLayout>(R.id.instructionContainer)
            for (ins in recipe!!.instructions) {
                val instructionView = TextView(instructionContainer.context)
                instructionView.text = ins
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(10, 35, 0, 0)
                instructionView.layoutParams = params
                instructionView.gravity = CENTER or LEFT
                instructionContainer.addView(instructionView)
            }

        }

        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            if (b != null) {
                val previous = b.getBoolean("createRecipe")
                if (previous) {
                    val intent = Intent(this, RecipeActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
            finish()
        }
    }


}
