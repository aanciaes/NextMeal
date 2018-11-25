package pt.unl.fct.mealroullete.homepage.calculator

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_calculator.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.history.HistoryActivity
import pt.unl.fct.mealroullete.homepage.poll.PollActivity
import pt.unl.fct.mealroullete.homepage.profile.ProfileActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import pt.unl.fct.mealroullete.logout.LogoutActivity
import pt.unl.fct.mealroullete.persistance.Ingredient
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.User
import java.io.File


class CalculatorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val cachedIngredient = mutableListOf<Ingredient>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_calculator)
        setSupportActionBar(calculator_toolbar)
        cachedIngredient.addAll(MockDatabase.mainCourseItems)
        cachedIngredient.addAll(MockDatabase.sideItems)

        buildList(listOf())

        val toggle = ActionBarDrawerToggle(this, calculator_drawer, calculator_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        calculator_drawer.addDrawerListener(toggle)
        toggle.syncState()

        calculator_navbar.setCheckedItem(R.id.common_drawer_item_calculator)
        calculator_navbar.setNavigationItemSelectedListener(this)

        setCommonHeaderInformationForLoggedInUser(calculator_navbar, MockDatabase.loggedInUser)

        val search = findViewById<EditText>(R.id.search_field)
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.trim().isEmpty()) {
                        buildList(listOf())
                    } else {
                        val items = cachedIngredient.filter { it.name.contains(s, true) }
                        buildList(items)
                    }
                } else {
                    buildList(listOf())
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
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

    private fun buildList(ingredients: List<Ingredient>) {
        val container = findViewById<TableLayout>(R.id.ingredient_table)
        container.removeAllViews()

        val inflater = LayoutInflater.from(this)

        for (ingredient in ingredients) {
            val child = inflater.inflate(R.layout.table_item_calculator, container, false) as LinearLayout

            child.findViewById<TextView>(R.id.calculatorName).text = ingredient.name
            child.findViewById<TextView>(R.id.calculatorCalories).text = ingredient.calories.toString()
            child.findViewById<TextView>(R.id.calculatorProtein).text = ingredient.protein.toString()
            child.findViewById<TextView>(R.id.calculatorCarbs).text = ingredient.carbs.toString()
            child.findViewById<TextView>(R.id.calculatorFats).text = ingredient.fats.toString()

            child.findViewById<ImageButton>(R.id.add_ingredient_calc).setOnClickListener {

                val contain = findViewById<TableLayout>(R.id.ingredient_table_calc)

                val row = inflater.inflate(R.layout.table_item_calculator, contain, false) as LinearLayout

                val wrapper = row.findViewById<LinearLayout>(R.id.table_item_calculator)
                //val params = wrapper.getLayoutParams()
                //params.height = 50
                //wrapper.setLayoutParams(params)

                val calories = findViewById<TextView>(R.id.caloriesTotal)
                val newCal = (Integer.parseInt(calories.text.toString()) + ingredient.calories).toString()
                calories.text = newCal

                val fats = findViewById<TextView>(R.id.fatsTotal)
                val newFats = (Integer.parseInt(fats.text.toString()) + ingredient.fats).toString()
                fats.text = newFats

                val protein = findViewById<TextView>(R.id.proteinTotal)
                val newProtein = (Integer.parseInt(protein.text.toString()) + ingredient.protein).toString()
                protein.text = newProtein

                val carbs = findViewById<TextView>(R.id.carbsTotal)
                val newCarbs = (Integer.parseInt(carbs.text.toString()) + ingredient.carbs).toString()
                carbs.text = newCarbs

                row.findViewById<TextView>(R.id.calculatorName).text = ingredient.name
                row.findViewById<TextView>(R.id.calculatorCalories).text = ingredient.calories.toString()
                row.findViewById<TextView>(R.id.calculatorProtein).text = ingredient.protein.toString()
                row.findViewById<TextView>(R.id.calculatorCarbs).text = ingredient.carbs.toString()
                row.findViewById<TextView>(R.id.calculatorFats).text = ingredient.fats.toString()

                row.findViewById<ImageButton>(R.id.add_ingredient_calc).setImageResource(R.drawable.remove_icon)

                row.findViewById<ImageButton>(R.id.add_ingredient_calc).setOnClickListener {
                    val calories = findViewById<TextView>(R.id.caloriesTotal)
                    val newCal = (Integer.parseInt(calories.text.toString()) - ingredient.calories).toString()
                    calories.text = newCal

                    val fats = findViewById<TextView>(R.id.fatsTotal)
                    val newFats = (Integer.parseInt(fats.text.toString()) - ingredient.fats).toString()
                    fats.text = newFats

                    val protein = findViewById<TextView>(R.id.proteinTotal)
                    val newProtein = (Integer.parseInt(protein.text.toString()) - ingredient.protein).toString()
                    protein.text = newProtein

                    val carbs = findViewById<TextView>(R.id.carbsTotal)
                    val newCarbs = (Integer.parseInt(carbs.text.toString()) - ingredient.carbs).toString()
                    carbs.text = newCarbs

                    contain.removeView(row)
                }
                contain.addView(row)
            }
            container.addView(child)
        }
    }
}
