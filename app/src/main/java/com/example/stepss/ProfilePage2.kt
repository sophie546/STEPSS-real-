package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfilePage2 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page2)

        val edit_profile = findViewById<Button>(R.id.edit_profile)
        val backArrow: ImageView = findViewById(R.id.back_button)

        val nameTextView = findViewById<TextView>(R.id.name)
        val emailTextView = findViewById<TextView>(R.id.email)
        val contactNumberTextView = findViewById<TextView>(R.id.contact_number)
        val locationTextView = findViewById<TextView>(R.id.location)

        // Get the passed data from the intent
        val currentName = intent.getStringExtra("CURRENT_NAME")
        val currentEmail = intent.getStringExtra("CURRENT_EMAIL")

        // Set the placeholders
        nameTextView.text = currentName ?: "No name provided"
        emailTextView.text = currentEmail ?: "No email provided"



        backArrow.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        edit_profile.setOnClickListener {
            val intent = Intent(this, EditProfilePage::class.java).apply {
                intent.putExtra("CURRENT_NAME", currentName)
                intent.putExtra("CURRENT_EMAIL", currentEmail)
            }
            startActivityForResult(intent, 1001)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}