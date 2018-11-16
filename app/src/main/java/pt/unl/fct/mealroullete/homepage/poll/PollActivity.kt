package pt.unl.fct.mealroullete.homepage.poll

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import pt.unl.fct.mealroullete.homepage.history.HistoryActivity
import pt.unl.fct.mealroullete.homepage.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_poll.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.calculator.CalculatorActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import pt.unl.fct.mealroullete.logout.LogoutActivity
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.User

class PollActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll)
        setSupportActionBar(poll_toolbar)

        val toggle = ActionBarDrawerToggle(this, poll_drawer, poll_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        poll_drawer.addDrawerListener(toggle)
        toggle.syncState()

        poll_navbar.setCheckedItem(R.id.common_drawer_item_poll)
        poll_navbar.setNavigationItemSelectedListener(this)

        poll_pager.adapter = PollPagerAdapter(supportFragmentManager)
        poll_tab.setupWithViewPager(poll_pager)

        setCommonHeaderInformationForLoggedInUser(poll_navbar, MockDatabase.loggedInUser)
    }

    override fun onBackPressed() {
        if (poll_drawer.isDrawerOpen(GravityCompat.START)) {
            poll_drawer.closeDrawer(GravityCompat.START)
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
                intent = Intent(this, HistoryActivity::class.java)
            }
            R.id.common_drawer_item_poll -> {
                poll_drawer.closeDrawer(GravityCompat.START)
                return false
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

        poll_drawer.closeDrawer(GravityCompat.START)

        startActivity(intent)
        this.finish()

        return true
    }

    private fun setCommonHeaderInformationForLoggedInUser (navigationView: NavigationView, user: User?) {
        val header = navigationView.getHeaderView(0)

        header.findViewById<TextView>(R.id.common_header_user_full_name).text = user?.username
        header.findViewById<TextView>(R.id.common_header_user_email_address).text = user?.email
        //TODO: Set profile image
    }
}
