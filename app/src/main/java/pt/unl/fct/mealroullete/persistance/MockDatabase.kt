package pt.unl.fct.mealroullete.persistance



object MockDatabase {

    private val users = mutableListOf<User>()

    init {
        users.add(User(1, "admin", "admin"))
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