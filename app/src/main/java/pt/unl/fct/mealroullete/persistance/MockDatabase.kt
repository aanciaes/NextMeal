package pt.unl.fct.mealroullete.persistance



object MockDatabase {

    private val users = mutableListOf<User>()
    val mainCourseItems = listOf("Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva", "carne de porco",
            "Atum", "Carne de vaca", "relva")

    val sideItems = listOf("arroz", "batata", "feijao verde", "feijao manteinga", "nabiças",
            "funcho", "cenouras")

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
            if (it.username == username && it.password == password)
                return true
        }
        return false
    }
}



class User (val id: Long, val username: String, val password: String)
