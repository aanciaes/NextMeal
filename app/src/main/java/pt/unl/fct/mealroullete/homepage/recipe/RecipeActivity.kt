package pt.unl.fct.mealroullete.homepage.recipe

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_recipe.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.calculator.CalculatorActivity
import pt.unl.fct.mealroullete.homepage.history.HistoryActivity
import pt.unl.fct.mealroullete.homepage.poll.PollActivity
import pt.unl.fct.mealroullete.homepage.profile.ProfileActivity
import pt.unl.fct.mealroullete.logout.LogoutActivity
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.User
import java.io.File


class RecipeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        setSupportActionBar(recipe_toolbar)

        val toggle = ActionBarDrawerToggle(this, recipe_drawer, recipe_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        recipe_drawer.addDrawerListener(toggle)
        toggle.syncState()

        recipe_navbar.setCheckedItem(R.id.common_drawer_item_recipe)
        recipe_navbar.setNavigationItemSelectedListener(this)

        recipe_pager.adapter = RecipePagerAdapter(supportFragmentManager)
        recipe_tab.setupWithViewPager(recipe_pager)

        setCommonHeaderInformationForLoggedInUser(recipe_navbar, MockDatabase.loggedInUser)
    }

    override fun onBackPressed() {
        if (recipe_drawer.isDrawerOpen(GravityCompat.START)) {
            recipe_drawer.closeDrawer(GravityCompat.START)
        } else {
            // Do nothing
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
                intent = Intent(this, ProfileActivity::class.java)
            }
            R.id.common_drawer_item_recipe -> {
                recipe_drawer.closeDrawer(GravityCompat.START)
                return false
            }
            R.id.common_drawer_item_logout -> {
                intent = Intent(this, LogoutActivity::class.java)
            }
        }

        recipe_drawer.closeDrawer(GravityCompat.START)

        startActivity(intent)
        this.finish()

        return true
    }

    private fun setCommonHeaderInformationForLoggedInUser(navigationView: NavigationView, user: User?) {
        val header = navigationView.getHeaderView(0)

        header.findViewById<TextView>(R.id.common_header_user_full_name).text = user?.username
        header.findViewById<TextView>(R.id.common_header_user_email_address).text = user?.email


        val imageView = header.findViewById<ImageView>(R.id.common_header_user_profile_photo)

        if (MockDatabase.loggedInUser?.picture != null) {
            setImageFromUri(MockDatabase.loggedInUser?.picture.toString(), imageView)
        }
    }

    private fun setImageFromUri(uri: String, imageView: ImageView) {
        imageView.setImageURI(Uri.parse(uri))
    }
}
