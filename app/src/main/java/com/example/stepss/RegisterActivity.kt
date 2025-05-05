package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : Activity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        // Initialize EditText fields
        editTextUsername = findViewById(R.id.edit_text_username)
        editTextPassword = findViewById(R.id.edit_text_password)
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password)

        val buttonSignUpReg = findViewById<Button>(R.id.button_SignUpReg)
        buttonSignUpReg.setOnClickListener {
            if (validateInputs()) {
                val username = editTextUsername.text.toString().trim()
                val password = editTextPassword.text.toString().trim()
                saveUserData(username, password)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        val buttonLogin = findViewById<Button>(R.id.button_login)
        buttonLogin.setOnClickListener {
            Log.e("Sample Project", "Button is clicked!")
            Toast.makeText(this, "Button is clicked!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Validation for empty inputs and matching passwords
    private fun validateInputs(): Boolean {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()

        if (username.isEmpty()) {
            editTextUsername.error = "Username is required"
            return false
        }

        if (password.isEmpty()) {
            editTextPassword.error = "Password is required"
            return false
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.error = "Confirm Password is required"
            return false
        }

        if (password != confirmPassword) {
            editTextConfirmPassword.error = "Passwords do not match"
            return false
        }

        return true
    }

    // SAVE USER INPUTTED DATA
    private fun saveUserData(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.putString("PASSWORD", password)

        // Set default values for email, contact, and location (to be updated later)
        editor.putString("EMAIL", null)
        editor.putString("CONTACT", null)
        editor.putString("LOCATION", null)

        editor.apply() // Save data asynchronously
        Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
    }
}
