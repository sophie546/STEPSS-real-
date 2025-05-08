package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class LoginActivity : Activity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUpLogin: Button
    private lateinit var buttonCreateAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val gifImageView = findViewById<ImageView>(R.id.gifViewLogin)
        Glide.with(this).asGif().load(R.drawable.walkinganimation_nobackground2).into(gifImageView)

        editTextUsername = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUpLogin = findViewById(R.id.button_SignUpLogin)
        buttonCreateAccount = findViewById(R.id.button_CreateAccount)

        // Check if a user is already registered
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)
        val savedPassword = sharedPreferences.getString("PASSWORD", null)

        // Login button logic
        buttonSignUpLogin.setOnClickListener {
            if (validateInputs()) {
                if (checkCredentials()) {
                    Log.d("LoginActivity", "Login successful for user: ${editTextUsername.text}")
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LandingPageActivity::class.java))
                    finish()
                } else {
                    Log.d("LoginActivity", "Login failed for user: ${editTextUsername.text}")
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        buttonCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    // Check for empty fields and validate input format
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
            password.length < 6 -> {
                editTextPassword.error = "Password must be at least 6 characters"
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

        // Log the credential check for debugging
        Log.d("LoginActivity", "Checking credentials - Input: $inputUsername, Saved: $savedUsername")

        // Check if credentials exist and match
        return if (savedUsername != null && savedPassword != null) {
            val credentialsMatch = savedUsername == inputUsername && savedPassword == inputPassword
            if (!credentialsMatch) {
                Log.d("LoginActivity", "Credentials mismatch - Username match: ${savedUsername == inputUsername}, Password match: ${savedPassword == inputPassword}")
            }
            credentialsMatch
        } else {
            Log.d("LoginActivity", "No saved credentials found")
            false
        }
    }
}
