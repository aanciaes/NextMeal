package pt.unl.fct.mealroullete.persistance

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.RequiresApi
import pt.unl.fct.mealroullete.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


object MockDatabase {

    private val users = mutableListOf<User>()
    var loggedInUser: User? = null

    init {
        users.add(User(1, "root", "root", "root@mealroullete.com"))
    }

    val mainCourseItems = listOf(Ingredient(1, "Tuna", R.drawable.atum, 20, 12, 34, 2),
            Ingredient(2, "Duck", R.drawable.pato, 30, 35, 12, 24),
            Ingredient(3, "Turkey", R.drawable.peru, 800, 24, 11, 56),
            Ingredient(4, "Pork", R.drawable.porco, 39, 59, 2, 5),
            Ingredient(5, "Sardine", R.drawable.sardinha, 26, 52, 34, 2),
            Ingredient(6, "Tofu", R.drawable.tofu, 7, 2, 3, 20))

    val sideItems = listOf(Ingredient(7, "Sweet Potato", R.drawable.batatadoce, 30, 13, 32, 21),
            Ingredient(8, "Carrot", R.drawable.cenoura, 98, 21, 39, 21),
            Ingredient(9, "Cabbage", R.drawable.couve, 26, 16, 42, 27),
            Ingredient(10, "Flower Cabbage", R.drawable.couveflor, 20, 12, 34, 51),
            Ingredient(11, "Cranberry Beans", R.drawable.cranberry_bean, 20, 28, 30, 6),
            Ingredient(12, "Cowpeas", R.drawable.feijaofrade, 42, 8, 14, 3),
            Ingredient(13, "Black Peas", R.drawable.feijaopreto, 20, 12, 34, 2),
            Ingredient(14, "Green Beans", R.drawable.feijaoverde, 40, 2, 4, 2),
            Ingredient(15, "Turnip Sprout", R.drawable.grelos, 20, 12, 34, 20),
            Ingredient(16, "Potato", R.drawable.potato, 100, 5, 2, 50),
            Ingredient(17, "Rice", R.drawable.rice, 20, 12, 9, 2),
            Ingredient(18, "Tomato", R.drawable.tomato, 60, 7, 3, 10),
            Ingredient(19, "Onion", R.drawable.onion, 70, 9, 10, 14))

    val recipe1: Recipe = buildRecipe(1, "Russian Cabbage Rolls with Gravy", R.drawable.recipe1,
            mutableListOf(mainCourseItems[3], sideItems[7], sideItems[1], sideItems[10], sideItems[11], sideItems[12]),
            mutableListOf("Make slits at the base of the cabbage or cut out the entire core. Fill a large saucepan about 1/3 full of water. Bring to a boil and add the whole cabbage. Cook for 15 to 20 minutes, turning occasionally, until cabbage has softened. Drain and cool. Cut the leaves off the cabbage one by one, trying to keep them intact. Cut out the tough, thick center ribs of the large leaves with a sharp knife.",
            "Bring 1 1/2 cups water and rice to a boil in a saucepan. Reduce heat to medium-low, cover, and simmer until rice is tender and liquid has been absorbed, 20 to 25 minutes.",
            "Heat 2 tablespoons oil in a skillet and cook 1/2 the chopped onions until soft and translucent, about 5 minutes. Combine cooked rice, cooked onions, ground beef, ground pork, salt, and pepper in a large bowl and mix with your hands until filling is well combined.))",
            "Lay 1 cabbage leaf on a flat surface. Place 1 tablespoon of filling at the base of a cabbage leaf. Overlap with the bottom of the leaf; fold in side edges and roll up. Repeat with remaining cabbage and filling.",
            "Heat remaining 1 tablespoon oil over medium-high heat and fry cabbage rolls until browned, 2 to 3 minutes on each side. Transfer to a large pot and sprinkle with remaining chopped onion and carrots.",
            "Stir together sour cream and tomato paste in a bowl until well combined. Whisk in 1 cup water and season sauce with salt and herbs. Pour sauce over the cabbage rolls.",
            "Cover pot and cook over a medium-low heat until cabbage rolls are cooked through and sauce has thickened, about 45 minutes."))
    val recipe2: Recipe = buildRecipe(2, "Marinated Tuna Steak", R.drawable.recipe2,
            mutableListOf(mainCourseItems[0], sideItems[1], sideItems[10], sideItems[11], sideItems[12]),
            mutableListOf("In a large non-reactive dish, mix together the orange juice, soy sauce, olive oil, lemon juice, parsley, garlic, oregano, and pepper. Place the tuna steaks in the marinade and turn to coat. Cover, and refrigerate for at least 30 minutes.",
                    "Preheat grill for high heat.",
                    "Lightly oil grill grate. Cook the tuna steaks for 5 to 6 minutes, then turn and baste with the marinade. Cook for an additional 5 minutes, or to desired doneness. Discard any remaining marinade."))
    val recipe3: Recipe = buildRecipe(3, "Tofu Salad", R.drawable.recipe3,
            mutableListOf(mainCourseItems[5], sideItems[1], sideItems[2], sideItems[11], sideItems[12]),
            mutableListOf("In a large bowl, mix the chili sauce, ginger, garlic, soy sauce, and sesame oil. Place tofu in the mixture, and marinate 1 hour in the refrigerator.",
                    "Bring a pot of water to a boil. Immerse the snow peas in the boiling water for 1 to 2 minutes, then immerse in a a bowl of cold water. Drain, and set aside.",
                    "Toss the peas, carrots, cabbage, and peanuts with the tofu and marinade to serve."))
    val recipe4: Recipe = buildRecipe(4, "Duck Adobo", R.drawable.recipe4,
            mutableListOf(mainCourseItems[1], sideItems[1], sideItems[2], sideItems[10], sideItems[11], sideItems[12]),
            mutableListOf("Season duck legs with salt and black pepper.",
                    "Heat vegetable oil in a large, deep skillet over medium-high heat; add duck legs, skin side down, and cook until browned, 3 to 4 minutes per side. Remove duck legs and drain all but 1 tablespoon of duck fat from the pan.",
                    "Cook onion in reserved duck fat over medium heat until onion begins to turn translucent, 3 to 4 minutes. Add garlic; cook and stir until fragrant, 1 to 2 minutes.",
                    "Stir chicken broth, rice vinegar, soy sauce, sambal chili paste, and bay leaves into onion mixture; bring to a simmer. Return duck legs to the skillet, loosely cover, and simmer until duck legs are tender and easily pierced with a fork, about 2 hours.",
                    "Remove cover from the skillet, increase heat to high, and cook until sauce is thick, about 5 minutes; season with salt and black pepper to taste."))
    val recipe5: Recipe = buildRecipe(5, "Herb Roasted Pork", R.drawable.recipe5,
            mutableListOf(mainCourseItems[3], sideItems[0], sideItems[1], sideItems[2], sideItems[11], sideItems[12]),
            mutableListOf("Preheat oven to 325 degrees F (165 degrees C).",
                    "In a bowl, combine sage, salt, pepper, and garlic. Rub thoroughly all over pork. Place pork in an uncovered roasting pan on the middle oven rack.",
                    "Bake in the preheated oven approximately 3 hours, or until the internal temperature reaches at least 145 degrees F (63 degrees C), depending upon your desired doneness.",
                    "Meanwhile, place sugar, cornstarch, vinegar, water, and soy sauce in a small saucepan. Heat, stirring occasionally, until mixture begins to bubble and thicken slightly. Brush roast with glaze 3 or 4 times during the last 1/2 hour of cooking. Pour remaining glaze over roast, and serve."))

    val recipesList = mutableListOf(recipe1, recipe2, recipe3, recipe4, recipe5)

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    val poll1 = Poll(1, "Jantar de Novembro", mutableMapOf(Pair("badjoraz@gmail.com", "Herb Roasted Pork"), Pair("miguel@hotmail.com", "Herb Roasted Pork"))
            , "root", listOf(recipe5, recipe3, recipe1), recipe5, LocalDate.of(2018, 11, 22).atStartOfDay(), false)
    @SuppressLint("NewApi")
    val poll2 = Poll(2, "Jantar de Outubro", mutableMapOf(Pair("badjoraz@gmail.com", "Tofu Salad"), Pair("miguel@hotmail.com", "Tofu Salad"))
            , "root", listOf(recipe5, recipe3, recipe1), recipe3, LocalDate.of(2018, 10, 22).atStartOfDay(), false)


    @RequiresApi(Build.VERSION_CODES.O)
    val polls = mutableListOf<Poll>(poll1, poll2)


    private fun count(): Int {
        return users.size
    }

    fun addUser(username: String, password: String, email: String) {
        users.add(User(count() + 1L, username, password, email))
    }

    fun userExists(username: String): Boolean {
        users.forEach {
            if (it.username == username)
                return true
        }
        return false
    }

    fun login(username: String, password: String): Boolean {
        users.forEach {
            if (it.username == username && it.password == password) {
                loggedInUser = it
                return true
            }
        }
        return false
    }

    fun logout() {
        loggedInUser = null
    }

    fun addRecipe(recipe: Recipe) {
        recipesList.add(recipe)
    }

    fun getRecipe(idx: Int) : Recipe {
       return recipesList[idx]
    }

    fun getTotalRecipes() : Int {
        return recipesList.size
    }

    fun findRecipe(name: String) : Recipe? {
        return recipesList.find { it.name == name }
    }

    fun findPoll(id: Int) : Poll? {
        return polls.find { it.id == id }
    }

    fun buildRecipe(id: Int, name: String, image: Int, ingredients: MutableList<Ingredient>, instructions: MutableList<String>): Recipe {
        val nutrients = mutableListOf(0, 0, 0, 0)
        val quantities = mutableListOf<Int>()

        for (i in ingredients) {
            nutrients[0] += i.calories
            nutrients[1] += i.protein
            nutrients[2] += i.fats
            nutrients[3] += i.carbs
        }


        for (i in ingredients.indices) {
           quantities.add(i,Random().nextInt((500 + 1) - 100) +  100)
        }

        return Recipe(id, image, name,
                ingredients,
                quantities,
                nutrients, instructions, nutrients[0], false)
    }
}


class User(val id: Long,
           val username: String,
           var password: String,
           var email: String) {

    var picture: String? = null
    var fullName: String? = null
    var dateOfBirth: String? = null
    val allergies = mutableListOf<String>()
}

data class Ingredient(val id: Int, val name: String, val image: Int, val calories: Int, val fats: Int, val protein: Int, val carbs: Int)

class Recipe(val id: Int,
             var image: Any,
             var name: String,
             val ingredients: MutableList<Ingredient>,
             val quantities : MutableList<Int>,
             val nutrients: MutableList<Int>,
             val instructions: MutableList<String>,
             var calories: Int,
             var removed: Boolean)

class Poll(val id: Int, val name: String, val users: MutableMap<String, String>, val owner: String, val recipes: List<Recipe>, var winner: Recipe?, val endTimestamp: LocalDateTime, var active: Boolean)