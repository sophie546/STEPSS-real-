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
        editTextUsername = findViewById(R.id.editTextEmail) // or rename it to editTextUsername in XML for clarity
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUpLogin = findViewById(R.id.button_SignUpLogin)
        buttonCreateAccount = findViewById(R.id.button_CreateAccount)

        // Button click listener
        buttonSignUpLogin.setOnClickListener {
            if (validateInputs() && checkCredentials()) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LandingPageActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCreateAccount.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Validate if fields are empty
    private fun validateInputs(): Boolean {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (username.isEmpty()) {
            editTextUsername.error = "Username is required"
            return false
        }

        if (password.isEmpty()) {
            editTextPassword.error = "Password is required"
            return false
        }

        return true
    }

    // Check credentials against stored user data
    private fun checkCredentials(): Boolean {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)
        val savedPassword = sharedPreferences.getString("PASSWORD", null)

        return username == savedUsername && password == savedPassword
    }
}
