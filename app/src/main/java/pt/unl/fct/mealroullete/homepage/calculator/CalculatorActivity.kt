package pt.unl.fct.mealroullete.homepage.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View.inflate
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import pt.unl.fct.mealroullete.homepage.history.HistoryActivity
import pt.unl.fct.mealroullete.homepage.poll.PollActivity
import pt.unl.fct.mealroullete.homepage.profile.ProfileActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import kotlinx.android.synthetic.main.activity_calculator.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.logout.LogoutActivity
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.User
import java.io.File
import android.view.LayoutInflater



class CalculatorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setSupportActionBar(calculator_toolbar)
        val container = findViewById<TableLayout>(R.id.ingredient_table)
        val inflater = LayoutInflater.from(this)
        var x = 1
        while(x < 4){
            val child = inflater.inflate(R.layout.table_item_calculater, container, false) as LinearLayout
            child.findViewById<TextView>(R.id.calculatorName).text = "Ingredient Name" + x

            container.addView(child)
            x++
        }

        val toggle = ActionBarDrawerToggle(this, calculator_drawer, calculator_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        calculator_drawer.addDrawerListener(toggle)
        toggle.syncState()

        calculator_navbar.setCheckedItem(R.id.common_drawer_item_calculator)
        calculator_navbar.setNavigationItemSelectedListener(this)

        setCommonHeaderInformationForLoggedInUser (calculator_navbar, MockDatabase.loggedInUser)
    }

    override fun onBackPressed() {
        if (calculator_drawer.isDrawerOpen(GravityCompat.START)) {
            calculator_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent? = null

        when (item.itemId) {
            R.id.common_drawer_item_calculator -> {
                calculator_drawer.closeDrawer(GravityCompat.START)
                return false
            }
            R.id.common_drawer_item_history -> {
                intent = Intent(this, HistoryActivity::class.java)
            }
            R.id.common_drawer_item_poll -> {
                intent = Intent(this, PollActivity::class.java)
            }
            R.id.common_drawer_item_profile -> {
                intent = Intent(this, ProfileActivity::class.java)
            }
            R.id.common_drawer_item_recipe -> {
                intent = Intent(this, RecipeActivity::class.java)
            }
            R.id.common_drawer_item_logout -> {
                intent = Intent(this, LogoutActivity::class.java)
            }
        }

        calculator_drawer.closeDrawer(GravityCompat.START)

        startActivity(intent)
        this.finish()

        return true
    }

    private fun setCommonHeaderInformationForLoggedInUser (navigationView: NavigationView, user: User?) {
        val header = navigationView.getHeaderView(0)

        header.findViewById<TextView>(R.id.common_header_user_full_name).text = user?.username
        header.findViewById<TextView>(R.id.common_header_user_email_address).text = user?.email

        val imageView = header.findViewById<ImageView>(R.id.common_header_user_profile_photo)

        if (MockDatabase.loggedInUser?.picture != null) {
            setImageFromUrl(MockDatabase.loggedInUser?.picture.toString(), imageView)
        }
    }

    private fun setImageFromUrl (path: String, imageView: ImageView) {
        val imgFile = File(path);
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile (imgFile.absolutePath);

            imageView.setImageBitmap(myBitmap);
        }
    }
}
