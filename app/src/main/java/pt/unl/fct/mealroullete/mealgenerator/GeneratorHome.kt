package pt.unl.fct.mealroullete.mealgenerator

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.activity_generator_home.*
import pt.unl.fct.mealroullete.mealgenerator.customize.AdvancedGeneratorStep1
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity

class GeneratorHome : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
            }
            R.id.navigation_dashboard -> {

            }
            R.id.navigation_notifications -> {

            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generator_home)

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this, RecipeActivity::class.java))
        }
        val advancedGeneratorStep1 = findViewById<Button>(R.id.customize)
        advancedGeneratorStep1.setOnClickListener {
            startActivity(Intent(this, AdvancedGeneratorStep1::class.java))
        }
    }
}
