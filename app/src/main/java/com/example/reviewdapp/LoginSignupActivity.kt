package com.example.reviewdapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginSignupActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        // Initialize components
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonSignUp = findViewById(R.id.buttonSignUp)

        // Shared Preferences to store user credentials (for demo purposes)
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (validateInputs(username, password)) {
                val registeredPassword = sharedPref.getString(username, null)
                if (registeredPassword != null && registeredPassword == password) {
                    // Login successful, navigate to MainActivity
                    navigateToMainActivity()
                } else {
                    showError("Invalid credentials")
                }
            }
        }

        buttonSignUp.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (validateInputs(username, password)) {
                val editor = sharedPref.edit()
                editor.putString(username, password)
                editor.apply()
                // Signup successful, navigate to MainActivity
                navigateToMainActivity()
            }
        }
    }

    private fun validateInputs(username: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                showError("Username cannot be empty")
                false
            }

            password.isEmpty() -> {
                showError("Password cannot be empty")
                false
            }

            else -> true
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close the login/signup activity
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}
