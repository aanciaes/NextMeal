package pt.unl.fct.mealroullete.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.register.RegisterActivity
import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.support.constraint.ConstraintLayout
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setMainListeners()
        setUsernameFromRegisterActivity(intent.getStringExtra("registeredUsername"))
    }

    // Set all listeners for this activity
    private fun setMainListeners() {
        setupHideKeyboard()
        onClickShowPassword()
        loginWithFingerprint()
        noAccountYetRegister()
        login()
    }

    private fun setUsernameFromRegisterActivity(username: String?) {
        if (username != null) {
            val usernameField = findViewById<EditText>(R.id.username_field)
            usernameField.setText(username, TextView.BufferType.EDITABLE)
        }
    }

    // Changes between hidden or clear text password
    private fun onClickShowPassword() {
        val showHidePassword = findViewById<ImageButton>(R.id.show_hide_password)
        showHidePassword.setOnClickListener {
            val passwordField = findViewById<EditText>(R.id.password_field)

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
        dialog.setContentView(R.layout.fingerprint_dialog_content)

        val usePassword = dialog.findViewById<Button>(R.id.use_password_button)
        usePassword.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    // Changes from password authentication to fingerprint authentication
    private fun loginWithFingerprint() {
        val loginWithFingerPrint = findViewById<ImageButton>(R.id.fingerprint_login)
        loginWithFingerPrint.setOnClickListener {
            showFingerprintLogin()
        }
    }

    // Jumps to register activity
    private fun noAccountYetRegister() {
        val registerJump = findViewById<TextView>(R.id.register_text)
        registerJump.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Performs simple username/password authentication
    private fun login() {
        val login = findViewById<CardView>(R.id.login_button_card_view)
        login.setOnClickListener {
            val username = findViewById<EditText>(R.id.username_field)
            val password = findViewById<EditText>(R.id.password_field)

            if (MockDatabase.login(username.text.toString(), password.text.toString())) {
                startActivity(Intent(this, RecipeActivity::class.java))
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupHideKeyboard() {
        val constraintLayout = findViewById<TextView>(R.id.login_container) as? ConstraintLayout
        constraintLayout?.setOnClickListener() {
            defocusAndHideKeyboard(this)
        }
    }

    override fun onBackPressed() {
        //Do nothing
    }

    fun defocusAndHideKeyboard(activity: Activity?) {
        if (activity == null) return
        val view = activity.currentFocus
        if (view != null) {
            view.clearFocus()
            hideKeyboard(activity, view.windowToken)
        }
    }

    fun hideKeyboard(activity: Activity?, windowToken: IBinder?) {
        if (activity == null) return
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}