package pt.unl.fct.mealroullete.logout

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.login.LoginActivity
import pt.unl.fct.mealroullete.persistance.MockDatabase

class LogoutActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        logout()
        showRegisterSuccessful()
    }

    private fun logout () {
        MockDatabase.logout()
        //startActivity(Intent(this, LoginActivity::class.java))
    }


    // Shows logout successful dialog
    private fun showRegisterSuccessful() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.setMessage("Logout success")
        builder.apply {
            setPositiveButton("OK") { _, _ ->
                startActivity(Intent(this.context, LoginActivity::class.java))
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}