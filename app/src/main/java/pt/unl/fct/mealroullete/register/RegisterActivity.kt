package pt.unl.fct.mealroullete.register

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.inputmethod.InputMethodManager
import android.widget.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.login.LoginActivity
import pt.unl.fct.mealroullete.persistance.MockDatabase

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
        addListeners()
    }

    private fun addListeners() {
        setupHideKeyboard()
        checkUsername()
        registerListener()
        onClickShowPassword()
        onClickShowRepeatPassword()
        passwordsMatch()
        backToLoginListener()
    }

    private fun checkUsername() {
        val availability = findViewById<TextView>(R.id.username_availability)
        val username = findViewById<EditText>(R.id.register_username_field)
        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val availabilityImage = findViewById<ImageView>(R.id.username_availability_image)

                if (count == 0) { // If user deletes all text from username field
                    availabilityImage.setImageResource(R.drawable.ic_action_check_username_negative)
                    availability.text = "negative"
                } else {
                    if (checkUsernameAvailability(s.toString())) {
                        availabilityImage.setImageResource(R.drawable.ic_action_check_username_negative)
                        availability.text = "negative"
                    } else {
                        availabilityImage.setImageResource(R.drawable.ic_action_check_username_positive)
                        availability.text = "positive"
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun checkUsernameAvailability(username: String): Boolean {
        return MockDatabase.userExists(username)
    }

    private fun registerListener() {
        val register = findViewById<CardView>(R.id.register_button)

        register.setOnClickListener {
            val username = findViewById<EditText>(R.id.register_username_field)
            val availability = findViewById<TextView>(R.id.username_availability)
            val password = findViewById<EditText>(R.id.register_password_field)
            val repeatPassword = findViewById<EditText>(R.id.register_repeat_password_field)
            val email = findViewById<EditText>(R.id.register_email_field)


            if (username.text.isEmpty() || password.text.isEmpty() || repeatPassword.text.isEmpty() || email.text.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else {
                if (availability.text == "negative") {
                    Toast.makeText(this, "Username is not available", Toast.LENGTH_LONG).show()
                } else {
                    if (password.text.toString() != repeatPassword.text.toString()) {
                        Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                    } else {
                        MockDatabase.addUser(username.text.toString(), password.text.toString(), email.text.toString())
                        showRegisterSuccessful(username.text.toString())
                    }
                }
            }
        }
    }

    // Shows register successful dialog
    private fun showRegisterSuccessful(username: String) {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.setMessage("Account created with success")
        builder.apply {
            setPositiveButton("OK") { _, _ ->
                val intent: Intent = Intent(this.context, LoginActivity::class.java)
                intent.putExtra("registeredUsername", username)
                startActivity(intent)
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Changes between hidden or clear text password
    private fun onClickShowPassword() {
        val showHidePassword = findViewById<ImageButton>(R.id.register_show_hide_password)
        showHidePassword.setOnClickListener {
            val passwordField = findViewById<EditText>(R.id.register_password_field)

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
        val showHidePassword = findViewById<ImageButton>(R.id.register_show_hide_repeat_password_image)
        showHidePassword.setOnClickListener {
            val passwordField = findViewById<EditText>(R.id.register_repeat_password_field)

            if (passwordField.transformationMethod is PasswordTransformationMethod) {
                passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance();
                showHidePassword.setImageResource(R.drawable.ic_action_hide_password)
            } else {
                passwordField.transformationMethod = PasswordTransformationMethod.getInstance();
                showHidePassword.setImageResource(R.drawable.ic_action_show_password)
            }
        }
    }

    private fun passwordsMatch() {
        val repeatPasswordField = findViewById<EditText>(R.id.register_repeat_password_field)

        repeatPasswordField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordField = findViewById<EditText>(R.id.register_password_field)

                if (s.isNullOrEmpty() || s.toString() != passwordField.text.toString()) {
                    repeatPasswordField.setBackgroundResource(R.drawable.password_matching_negative)
                } else {
                    repeatPasswordField.setBackgroundResource(R.drawable.password_matching_positive)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun backToLoginListener() {
        val registerJump = findViewById<TextView>(R.id.back_to_login)
        registerJump.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setupHideKeyboard() {
        val constraintLayout = findViewById<TextView>(R.id.register_container) as? ConstraintLayout
        constraintLayout?.setOnClickListener() {
            defocusAndHideKeyboard(this)
        }
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