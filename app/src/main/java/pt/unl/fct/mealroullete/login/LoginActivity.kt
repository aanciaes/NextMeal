package pt.unl.fct.mealroullete.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setMainListeners()
    }

    // Set all listeners for this activity
    private fun setMainListeners() {
        onClickShowPassword()
        loginWithFingerprint();
        noAccountYetRegister()
        login()
    }

    // Changes between hidden or clear text password
    private fun onClickShowPassword() {
        val showHidePassword = findViewById<ImageButton>(R.id.showHidePassword)
        showHidePassword.setOnClickListener {
            val passwordField = findViewById<EditText>(R.id.passwordField)

            if (passwordField.transformationMethod is PasswordTransformationMethod) {
                passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance();
                showHidePassword.setImageResource(R.drawable.ic_action_hide_password)
            } else {
                passwordField.transformationMethod = PasswordTransformationMethod.getInstance();
                showHidePassword.setImageResource(R.drawable.ic_action_show_password)
            }
        }
    }

    // Shows fingerprint dialog
    private fun showFingerprintLogin() {
        val dialog = Dialog(this);
        dialog.setContentView(R.layout.fingerprint_dialog_content);

        val usePassword = dialog.findViewById<Button>(R.id.usePasswordButton)
        usePassword.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    // Changes from password authentication to fingerprint authentication
    private fun loginWithFingerprint() {
        val loginWithFingerPrint = findViewById<ImageButton>(R.id.fingerprintLogin)
        loginWithFingerPrint.setOnClickListener {
            showFingerprintLogin()
        }
    }

    // Jumps to register activity
    private fun noAccountYetRegister() {
        val registerJump = findViewById<TextView>(R.id.registerText)
        registerJump.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Performs simple username/password authentication
    // TODO: jump to home page
    private fun login() {
        val login = findViewById<CardView>(R.id.loginButtonCardView)
        login.setOnClickListener {
            val username = findViewById<EditText>(R.id.usernameField)
            val password = findViewById<EditText>(R.id.passwordField)

            if (MockDatabase.login(username.text.toString(), password.text.toString())) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_LONG).show();
            }
        }
    }
}