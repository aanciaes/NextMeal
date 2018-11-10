package pt.unl.fct.mealroullete.homepage.calculator

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import pt.unl.fct.mealroullete.homepage.history.HistoryActivity
import pt.unl.fct.mealroullete.homepage.poll.PollActivity
import pt.unl.fct.mealroullete.homepage.profile.ProfileActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import kotlinx.android.synthetic.main.activity_calculator.*
import pt.unl.fct.mealroullete.R

class CalculatorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setSupportActionBar(calculator_toolbar)

        val toggle = ActionBarDrawerToggle(this, calculator_drawer, calculator_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        calculator_drawer.addDrawerListener(toggle)
        toggle.syncState()

        calculator_navbar.setCheckedItem(R.id.common_drawer_item_calculator)
        calculator_navbar.setNavigationItemSelectedListener(this)
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
        }

        calculator_drawer.closeDrawer(GravityCompat.START)

        startActivity(intent)
        this.finish()

        return true
    }
}
