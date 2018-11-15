package pt.unl.fct.mealroullete.persistance



object MockDatabase {

    private val users = mutableListOf<User>()
    val mainCourseItems = listOf("Atum", "Carne de Vaca", "Ricardo", "Carne de Porco",
            "Manel", "Amaral", "Rodrigues")

    val sideItems = listOf("arroz", "batata", "feijao verde", "feijao manteinga", "nabiças",
            "funcho", "cenouras")

    var loggedInUser: User? = null

    init {
        users.add(User(1, "root", "root"))
    }

    fun count (): Int {
        return users.size
    }

    fun addUser (username: String, password: String) {
        users.add(User(count() + 1L, username, password))
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
}



class User (val id: Long, val username: String, val password: String)
