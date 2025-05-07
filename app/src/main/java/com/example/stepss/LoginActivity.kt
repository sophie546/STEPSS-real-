package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUpLogin: Button
    private lateinit var buttonCreateAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        // Initialize views
        editTextUsername = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUpLogin = findViewById(R.id.button_SignUpLogin)
        buttonCreateAccount = findViewById(R.id.button_CreateAccount)

        // Login button
        buttonSignUpLogin.setOnClickListener {
            if (validateInputs()) {
                if (checkCredentials()) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LandingPageActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Navigate to register
        buttonCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    // Check for empty fields
    private fun validateInputs(): Boolean {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        return when {
            username.isEmpty() -> {
                editTextUsername.error = "Username is required"
                false
            }
            password.isEmpty() -> {
                editTextPassword.error = "Password is required"
                false
            }
            else -> true
        }
    }

    // Match credentials with stored data
    private fun checkCredentials(): Boolean {
        val inputUsername = editTextUsername.text.toString().trim()
        val inputPassword = editTextPassword.text.toString().trim()

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)
        val savedPassword = sharedPreferences.getString("PASSWORD", null)

        return savedUsername == inputUsername && savedPassword == inputPassword
    }
}
