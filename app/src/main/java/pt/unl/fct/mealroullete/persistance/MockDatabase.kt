package pt.unl.fct.mealroullete.persistance

import pt.unl.fct.mealroullete.mealgenerator.customize.Ingredient


object MockDatabase {

    private val users = mutableListOf<User>()
    val mainCourseItems = listOf("Atum", "Carne de Vaca", "Ricardo", "Carne de Porco",
            "Manel", "Amaral", "Rodrigues")

    val sideItems = listOf("arroz", "batata", "feijao verde", "feijao manteinga", "nabiças",
            "funcho", "cenouras")

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

class Recipe( val id: Int,
              val image: String,
              val name: String,
              val ingredients: List<String>,
              val nutrients: List<String>,
              val instructions: List<String>,
              val calories: Int)
