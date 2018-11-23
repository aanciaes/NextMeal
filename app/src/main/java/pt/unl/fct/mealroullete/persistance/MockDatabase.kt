package pt.unl.fct.mealroullete.persistance

import pt.unl.fct.mealroullete.R


object MockDatabase {

    private val users = mutableListOf<User>()
    val mainCourseItems = listOf(Ingredient(1, "Tuna", R.drawable.atum), Ingredient(2, "Duck", R.drawable.pato), Ingredient(3, "Turkey", R.drawable.peru), Ingredient(4, "Pork", R.drawable.porco)
    ,Ingredient(5, "Sardine", R.drawable.sardinha), Ingredient(6, "Tofu", R.drawable.tofu))

    val sideItems = listOf(Ingredient(7, "Sweet Potato", R.drawable.batatadoce), Ingredient(8, "Carrot", R.drawable.cenoura)
            , Ingredient(9, "Cabbage", R.drawable.couve), Ingredient(10, "Flower Cabbage", R.drawable.couveflor)
            ,Ingredient(11, "Cranberry Beans", R.drawable.cranberry_bean), Ingredient(12, "Cowpeas", R.drawable.feijaofrade)
            ,Ingredient(13, "Black Peas", R.drawable.feijaopreto), Ingredient(14, "Green Beans", R.drawable.feijaoverde)
            ,Ingredient(15, "Turnip Sprout", R.drawable.grelos), Ingredient(16, "Potato", R.drawable.potato)
            , Ingredient(17, "Rice", R.drawable.rice))

    val recipesList = mutableListOf<Recipe>().apply { add(Recipe(size+1, "", "Badjoraz a Ze do Pipo",
            listOf("2 pipos","3 bananas","1 ananas","10 frangos"),
            listOf("20g proteina","10g sal","4g hidratos de carbono","1g ferro"),
            listOf("1º Cozer a Banana","2º Juntar os pipos","3º Comer os frangos","4º Vomitar O ananas"),
            2000))
        add(Recipe(size+1, "", "Fni a Ze da Pipa",
            listOf("10 pipas","4 caes","1 penis","1/2 vagina"),
            listOf("10g proteina","1g sal","400g hidratos de carbono","10g ferro"),
            listOf("1º Cozer o cao","2º Juntar as pipas","3º Fritar a vagina","4º Vomitar o penis"),
            3400))
        add(Recipe(size+1, "", "Bibis assados",
                listOf("10 putos","4 carros","1 dente","2 ovelhas"),
                listOf("10000g proteina","1g sal","0g hidratos de carbono","0g ferro"),
                listOf("1º Cozer o puto","2º Juntar o bibi","3º Fritar um carro","4º Vomitar o bibi"),
                3400))}
    var loggedInUser: User? = null

    init {
        users.add(User(1, "root", "root", "root@nextmeal.com"))
    }

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

data class Ingredient (val id: Int, val name: String, val image: Int)

class Recipe( val id: Int,
              val image: String,
              val name: String,
              val ingredients: List<String>,
              val nutrients: List<String>,
              val instructions: List<String>,
              val calories: Int)
