package pt.unl.fct.mealroullete.persistance

import android.os.Build
import android.support.annotation.RequiresApi
import pt.unl.fct.mealroullete.R
import java.security.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


object MockDatabase {

    private val users = mutableListOf<User>()
    var loggedInUser: User? = null

    init {
        users.add(User(1, "root", "root", "root@mealroullete.com"))
    }

    val mainCourseItems = listOf(Ingredient(1, "Tuna", R.drawable.atum, 20, 12, 34, 2), Ingredient(2, "Duck", R.drawable.pato, 30, 35, 12, 24), Ingredient(3, "Turkey", R.drawable.peru, 800, 24, 11,56), Ingredient(4, "Pork", R.drawable.porco, 39, 59, 2, 5)
    ,Ingredient(5, "Sardine", R.drawable.sardinha, 26, 52, 34, 2), Ingredient(6, "Tofu", R.drawable.tofu, 7, 2, 3, 20))

    val sideItems = listOf(Ingredient(7, "Sweet Potato", R.drawable.batatadoce, 30, 13, 32, 21), Ingredient(8, "Carrot", R.drawable.cenoura, 98, 21, 39, 21)
            , Ingredient(9, "Cabbage", R.drawable.couve, 26, 16, 42, 27), Ingredient(10, "Flower Cabbage", R.drawable.couveflor, 20, 12, 34, 51)
            ,Ingredient(11, "Cranberry Beans", R.drawable.cranberry_bean, 20, 28, 30, 6), Ingredient(12, "Cowpeas", R.drawable.feijaofrade, 42, 8, 14, 3)
            ,Ingredient(13, "Black Peas", R.drawable.feijaopreto, 20, 12, 34, 2), Ingredient(14, "Green Beans", R.drawable.feijaoverde, 40, 2, 4, 2)
            ,Ingredient(15, "Turnip Sprout", R.drawable.grelos, 20, 12, 34, 20), Ingredient(16, "Potato", R.drawable.potato, 100, 5, 2, 50)
            , Ingredient(17, "Rice", R.drawable.rice, 20, 12, 9, 2))

   /* val recipesLisst = mutableListOf<Recipe>().apply { add(Recipe(size+1, 0, "Badjoraz a Ze do Pipo",
            listOf("2 pipos","3 bananas","1 ananas","10 frangos"),
            listOf("20g proteina","10g sal","4g hidratos de carbono","1g ferro"),
            listOf("1º Cozer a Banana","2º Juntar os pipos","3º Comer os frangos","4º Vomitar O ananas"),
            2000))
        add(Recipe(size+1, 0, "Fni a Ze da Pipa",
            listOf("10 pipas","4 caes","1 penis","1/2 vagina"),
            listOf("10g proteina","1g sal","400g hidratos de carbono","10g ferro"),
            listOf("1º Cozer o cao","2º Juntar as pipas","3º Fritar a vagina","4º Vomitar o penis"),
            3400))
        add(Recipe(size+1, 0, "Bibis assados",
                listOf("10 putos","4 carros","1 dente","2 ovelhas"),
                listOf("10000g proteina","1g sal","0g hidratos de carbono","0g ferro"),
                listOf("1º Cozer o puto","2º Juntar o bibi","3º Fritar um carro","4º Vomitar o bibi"),
                3400))}
*/

    val ingredients = listOf(mainCourseItems[0], sideItems[0], sideItems[2])
    val instructions = listOf("Pré-aquece o forno a 190ºC.",
            "Começa por lavar, descascar e preparar os vegetais",
            "depois deita na travessa de levar ao forno.",
            "Juntar as Batatas e adiciona também á travessa.",
            "Levar tudo ao forno, por uns 40 minutos.",
            "Retira do forno e está pronto a servir!")
    val recipe:Recipe = buildRecipe(1, "Filetes de Salmão", R.drawable.big_example_meal, ingredients, instructions )
    val recipe2:Recipe = buildRecipe(2, "Paletes de Batata", R.drawable.big_example_meal, listOf(mainCourseItems[2], sideItems[2], sideItems[4]), instructions )
    val recipe3:Recipe = buildRecipe(3, "Cascas de Peru", R.drawable.big_example_meal, listOf(mainCourseItems[3], sideItems[0], sideItems[4]), instructions )

    val recipesList = mutableListOf(recipe, recipe2, recipe3)

    @RequiresApi(Build.VERSION_CODES.O)
    val polls = mutableListOf<Poll>(Poll(1, "Party2017", listOf(users[0].username), "root", recipesList, recipesList[0], LocalDateTime.of(2017, 12, 24, 10, 30), false),
            Poll(2, "Party2018", listOf(users[0].username, "Marlene", "Teresa"), "root", recipesList, recipesList[1], LocalDateTime.of(2018, 11, 24, 2, 30), true),
            Poll(3, "Carnaval", listOf(users[0].username, "Marlene"), "root", recipesList, recipesList[2], LocalDateTime.of(2018, 12, 24, 10, 30), true))

    private fun count (): Int {
        return users.size
    }

    fun addUser (username: String, password: String, email: String) {
        users.add(User(count() + 1L, username, password, email))
    }

    fun userExists (username: String): Boolean {
        users.forEach {
            if (it.username == username)
                return true
        }
        return false
    }

    fun login (username: String, password: String): Boolean {
        users.forEach {
            if (it.username == username && it.password == password) {
                loggedInUser = it
                return true
            }
        }
        return false
    }

    fun logout () {
        loggedInUser = null
    }

    fun addRecipe(recipe: Recipe){
        recipesList.add(recipe)
    }
    fun buildRecipe(id:Int, name:String, image: Int, ingredients: List<Ingredient>, instructions: List<String>): Recipe {

        val nutrients = mutableListOf(0,0,0,0)
        for (i in ingredients){
            nutrients[0] += i.calories
            nutrients[1] += i.protein
            nutrients[2] += i.fats
            nutrients[3] += i.carbs
        }

        return Recipe(id, image, name,
                ingredients,
                nutrients, instructions, nutrients[0])
    }
}



class User (val id: Long,
            val username: String,
            var password: String,
            var email: String) {

    var picture: String? = null
    var fullName: String? = null
    var dateOfBirth: String? = null
    val allergies = mutableListOf<String>()
}

data class Ingredient (val id: Int, val name: String, val image: Int, val calories: Int, val fats: Int, val protein: Int, val carbs: Int )

class Recipe( val id: Int,
              val image: Int,
              val name: String,
              val ingredients: List<Ingredient>,
              val nutrients: List<Int>,
              val instructions: List<String>,
              val calories: Int)

class Poll(val id: Int, val name: String, val users : List<String>, val owner: String, val recipes: List<Recipe>, val winner: Recipe, val endTimestamp: LocalDateTime, val active: Boolean)
