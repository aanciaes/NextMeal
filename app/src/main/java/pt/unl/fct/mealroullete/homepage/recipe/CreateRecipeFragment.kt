package pt.unl.fct.mealroullete.homepage.recipe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.unl.fct.mealroullete.R
import android.R.string.cancel
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.text.InputType
import android.widget.*
import pt.unl.fct.mealroullete.mealgenerator.GeneratorHome
import pt.unl.fct.mealroullete.register.RegisterActivity


class CreateRecipeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_recipe, container, false)

        val addRecipe = view.findViewById<Button>(R.id.addRecipe)
        addRecipe.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)

            val text = TextView(this.context)
            text.text = "ARE YOU SURE YOU WANT TO ADD THIS RECIPE?"
            text.setTextColor(R.color.colorAccent)
            text.setPadding(40, 60, 40, 60)
            builder.setView(text)
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener{
                dialog, which -> text.text = text.text.toString()
                val refresh = Intent(this.context, RecipeActivity::class.java)
                startActivity(refresh)
            })

            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
        }

        val addIngredient = view.findViewById<ImageButton>(R.id.add_ingredient)
        addIngredient.setOnClickListener{
            var ingredientName = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("INGREDIENT")

            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Add", DialogInterface.OnClickListener {
                dialog, which -> ingredientName = input.text.toString()
                if(ingredientName != ""){
                    val container = view.findViewById<LinearLayout>(R.id.ingredientContainer)
                    val ingredient = TextView(this.context)
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 25, 0, 0)
                    ingredient.layoutParams = params
                    ingredient.text = ingredientName
                    val id = View.generateViewId()
                    ingredient.id = id
                    val index = container.indexOfChild(addIngredient)
                    container.addView(ingredient, index)
                }

            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

        val addInstruction = view.findViewById<ImageButton>(R.id.add_instruction)
        addInstruction.setOnClickListener{

            var instructionName = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("INSTRUCTION")

            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Add", DialogInterface.OnClickListener {
                dialog, which -> instructionName = input.text.toString()
                if(instructionName != ""){
                    val container = view.findViewById<LinearLayout>(R.id.instructionContainer)
                    val instruction = TextView(this.context)
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 25, 0, 0)
                    instruction.layoutParams = params
                    instruction.text = instructionName
                    val id = View.generateViewId()
                    instruction.id = id
                    val index = container.indexOfChild(addInstruction)
                    container.addView(instruction, index)
                }

            })

            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }
        val addNutrient = view.findViewById<ImageButton>(R.id.add_nutrient)
        addNutrient.setOnClickListener {

            var nutrientName = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("NUTRIENT")

            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Add", DialogInterface.OnClickListener {
                dialog, which -> nutrientName = input.text.toString()
                if(nutrientName != "") {
                    val container = view.findViewById<LinearLayout>(R.id.nutrientContainer)
                    val nutrient = TextView(this.context)
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 25, 0, 0)
                    nutrient.layoutParams = params
                    nutrient.text = nutrientName
                    val id = View.generateViewId()
                    nutrient.id = id
                    val index = container.indexOfChild(addNutrient)
                    container.addView(nutrient, index)
                }

            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

        return view
    }
}
