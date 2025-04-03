package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class EditProfilePage : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_page)

        val editTextName = findViewById<EditText>(R.id.edit_name)
        val editTextEmail = findViewById<EditText>(R.id.edit_email)
        val editTextTitle = findViewById<EditText>(R.id.edit_title)
        val editTextAddress = findViewById<EditText>(R.id.edit_location)
        val btnSave = findViewById<Button>(R.id.save_button)

        // Find the Back Arrow
        val backArrow: ImageView = findViewById(R.id.back_button)

        // Handle Back Arrow Click
        backArrow.setOnClickListener {
            redirectToLogin()
        }

        btnSave.setOnClickListener {
            val newName = editTextName.text.toString()
            val newEmail = editTextEmail.text.toString()
            val newTitle = editTextTitle.text.toString()
            val newAddress = editTextAddress.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("NEW_NAME", newName)
            resultIntent.putExtra("NEW_EMAIL", newEmail)
            resultIntent.putExtra("NEW_TITLE", newTitle)
            resultIntent.putExtra("NEW_ADDRESS", newAddress)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }


    }

    private fun redirectToLogin() {
        val intent = Intent(this, ProfilePage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears backstack
        startActivity(intent)
        finish()
    }
}
