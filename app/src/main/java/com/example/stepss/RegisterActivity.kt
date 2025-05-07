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

class RegisterActivity : Activity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        val gifImageView = findViewById<ImageView>(R.id.gifViewRegister)
        Glide.with(this).asGif().load(R.drawable.walkinganimation_nobackground2).into(gifImageView)

        // Initialize EditText fields
        editTextUsername = findViewById(R.id.edit_text_username)
        editTextPassword = findViewById(R.id.edit_text_password)
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password)

        val buttonSignUpReg = findViewById<Button>(R.id.button_SignUpReg)
        val buttonLogin = findViewById<Button>(R.id.button_login)

        buttonSignUpReg.setOnClickListener {
            if (validateInputs()) {
                val username = editTextUsername.text.toString().trim()
                val password = editTextPassword.text.toString().trim()
                saveUserData(username, password)

                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish() // Prevent back navigation to registration
            }
        }

        buttonLogin.setOnClickListener {
            Log.d("RegisterActivity", "Login button clicked")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // Validation for empty inputs and matching passwords
    private fun validateInputs(): Boolean {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()

        when {
            username.isEmpty() -> {
                editTextUsername.error = "Username is required"
                return false
            }
            password.isEmpty() -> {
                editTextPassword.error = "Password is required"
                return false
            }
            confirmPassword.isEmpty() -> {
                editTextConfirmPassword.error = "Confirm Password is required"
                return false
            }
            password != confirmPassword -> {
                editTextConfirmPassword.error = "Passwords do not match"
                return false
            }
        }

        return true
    }

    // Save user data using SharedPreferences
    private fun saveUserData(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("USERNAME", username)
        editor.putString("PASSWORD", password)
        editor.putString("EMAIL", null) // will be set in EditProfilePage
        editor.putString("CONTACT", null)
        editor.putString("LOCATION", null)
        editor.putString("PROFILE_IMAGE_URI", null) // default image will be shown if null

        editor.apply()
    }
}
