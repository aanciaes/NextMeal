package pt.unl.fct.mealroullete.homepage.profile

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import pt.unl.fct.mealroullete.homepage.history.HistoryActivity
import kotlinx.android.synthetic.main.activity_profile.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.calculator.CalculatorActivity
import pt.unl.fct.mealroullete.homepage.poll.PollActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(profile_toolbar)

        val toggle = ActionBarDrawerToggle(this, profile_drawer, profile_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        profile_drawer.addDrawerListener(toggle)
        toggle.syncState()

        profile_navbar.setCheckedItem(R.id.common_drawer_item_profile)
        profile_navbar.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (profile_drawer.isDrawerOpen(GravityCompat.START)) {
            profile_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var intent: Intent? = null

        when (item.itemId) {
            R.id.common_drawer_item_calculator -> {
                intent = Intent(this, CalculatorActivity::class.java)
            }
            R.id.common_drawer_item_history -> {
                intent = Intent(this, HistoryActivity::class.java)
            }
            R.id.common_drawer_item_poll -> {
                intent = Intent(this, PollActivity::class.java)
            }
            R.id.common_drawer_item_profile -> {
                profile_drawer.closeDrawer(GravityCompat.START)
                return false
            }
            R.id.common_drawer_item_recipe -> {
                intent = Intent(this, RecipeActivity::class.java)
            }
        }

        profile_drawer.closeDrawer(GravityCompat.START)

        startActivity(intent)
        this.finish()

        return true
    }
}