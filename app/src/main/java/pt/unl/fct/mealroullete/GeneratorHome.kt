package pt.unl.fct.mealroullete

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_generator_home.*

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

        val advancedGeneratorStep1 = findViewById<Button>(R.id.customize)
        advancedGeneratorStep1.setOnClickListener {
            startActivity(Intent(this, AdvancedGeneratorStep1::class.java))
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
