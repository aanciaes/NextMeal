package pt.unl.fct.mealroullete

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            else -> Toast.makeText(this, "item_id: ${item.itemId}", Toast.LENGTH_LONG).show();
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val image = findViewById<ImageButton>(R.id.dinner)
        image.setOnClickListener {
            startActivity(Intent(this, GeneratorHome::class.java))
        }

        val image2 = findViewById<ImageButton>(R.id.breakfast)
        image2.setOnClickListener {
            Toast.makeText(this, "item_id: ${it.id}", Toast.LENGTH_LONG).show()
        }

        val image3 = findViewById<ImageButton>(R.id.brunch)
        image3.setOnClickListener {
            Toast.makeText(this, "item_id: ${it.id}", Toast.LENGTH_LONG).show()
        }

        val image4 = findViewById<ImageButton>(R.id.lunch)
        image4.setOnClickListener {
            Toast.makeText(this, "item_id: ${it.id}", Toast.LENGTH_LONG).show()
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
