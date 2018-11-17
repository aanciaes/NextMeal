package pt.unl.fct.mealroullete.persistance

import pt.unl.fct.mealroullete.mealgenerator.customize.Ingredient


object MockDatabase {

    private val users = mutableListOf<User>()
    val mainCourseItems = listOf("Atum", "Carne de Vaca", "Ricardo", "Carne de Porco",
            "Manel", "Amaral", "Rodrigues")

    val sideItems = listOf("arroz", "batata", "feijao verde", "feijao manteinga", "nabiças",
            "funcho", "cenouras")

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
            var username: String,
            var password: String,
            var email: String) {

    var picture: String? = null
    var fullname: String? = null
    var dateOfBirth: String? = null
    val allergies: List<Ingredient> = mutableListOf()
}
