package pt.unl.fct.mealroullete.homepage.history

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_history.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.calculator.CalculatorActivity
import pt.unl.fct.mealroullete.homepage.poll.PollActivity
import pt.unl.fct.mealroullete.homepage.profile.ProfileActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity

class HistoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(history_toolbar)

        val toggle = ActionBarDrawerToggle(this, history_drawer, history_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        history_drawer.addDrawerListener(toggle)
        toggle.syncState()

        history_navbar.setCheckedItem(R.id.common_drawer_item_history)
        history_navbar.setNavigationItemSelectedListener(this)

        history_pager.adapter = HistoryPagerAdapter(supportFragmentManager)
        history_tab.setupWithViewPager(history_pager)
    }

    override fun onBackPressed() {
        if (history_drawer.isDrawerOpen(GravityCompat.START)) {
            history_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent? = null

        when (item.itemId) {
            R.id.common_drawer_item_calculator -> {
                intent = Intent(this, CalculatorActivity::class.java)
            }
            R.id.common_drawer_item_history -> {
                history_drawer.closeDrawer(GravityCompat.START)
                return false
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

        history_drawer.closeDrawer(GravityCompat.START)

        startActivity(intent)
        this.finish()

        return true
    }
}
