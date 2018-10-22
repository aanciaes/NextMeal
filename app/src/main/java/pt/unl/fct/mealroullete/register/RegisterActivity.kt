package pt.unl.fct.mealroullete.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import pt.unl.fct.mealroullete.MockDatabase
import pt.unl.fct.mealroullete.R

class RegisterActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        addListeners()
        //TODO fix password match
    }

    private fun addListeners () {
        checkUsername()
        registerListener()
        onClickShowPassword()
        onClickShowRepeatPassword()
        passwordsMatch()
    }

     private fun checkUsername () {
         val availability = findViewById<TextView>(R.id.availability)
         val username = findViewById<EditText>(R.id.usernameField)
         username.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                 val availabilityImage = findViewById<ImageView>(R.id.checkUsername)

                 if (checkUsernameAvailability(s.toString())) {
                     availabilityImage.setImageResource(R.drawable.ic_action_check_username_negative)
                     availability.text = "negative"
                 } else {
                     availabilityImage.setImageResource(R.drawable.ic_action_check_username_positive)
                     availability.text = "positive"
                 }
             }
             override fun afterTextChanged(s: Editable) {}
         })
     }

    private fun checkUsernameAvailability (username: String): Boolean {
        return MockDatabase.userExists(username)
    }

    private fun registerListener () {
        val register = findViewById<CardView>(R.id.registerButtonCardView)

        register.setOnClickListener {
            val username = findViewById<EditText>(R.id.usernameField)
            val availability = findViewById<TextView>(R.id.availability)
            val password = findViewById<EditText>(R.id.passwordField)
            val repeatPassword = findViewById<EditText>(R.id.repeatPasswordField)

            if (availability.text == "negative"){
                Toast.makeText(this, "Username is not available", Toast.LENGTH_LONG).show()
            } else {
                if (password.text.toString() != repeatPassword.text.toString()){
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                } else {
                    MockDatabase.addUser(username.text.toString(), password.text.toString())
                    Toast.makeText(this, "Account created with success", Toast.LENGTH_LONG).show()
                }
            }
        }
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

    // Changes between hidden or clear text repeat password
    private fun onClickShowRepeatPassword() {
        val showHidePassword = findViewById<ImageButton>(R.id.showHideRepeatPassword)
        showHidePassword.setOnClickListener {
            val passwordField = findViewById<EditText>(R.id.repeatPasswordField)

            if (passwordField.transformationMethod is PasswordTransformationMethod) {
                passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance();
                showHidePassword.setImageResource(R.drawable.ic_action_hide_password)
            } else {
                passwordField.transformationMethod = PasswordTransformationMethod.getInstance();
                showHidePassword.setImageResource(R.drawable.ic_action_show_password)
            }
        }
    }

    private fun passwordsMatch () {
        val repeatPasswordField = findViewById<EditText>(R.id.repeatPasswordField)

        repeatPasswordField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordsMatchView = findViewById<ImageView>(R.id.passwordsMatch)
                val passwordsMatchText = findViewById<TextView>(R.id.passwordsMatchText)
                val passwordField = findViewById<EditText>(R.id.passwordField)

                if (s != passwordField) {
                    passwordsMatchView.setImageResource(R.drawable.ic_action_check_username_negative)
                    passwordsMatchText.text = "negative"
                } else {
                    passwordsMatchView.setImageResource(R.drawable.ic_action_check_username_positive)
                    passwordsMatchText.text = "positive"
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }
}