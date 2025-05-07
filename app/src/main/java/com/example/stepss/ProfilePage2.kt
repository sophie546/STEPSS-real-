package com.example.stepss

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stepss.data.ProfileData

class ProfilePage2 : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page2)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val editProfileButton = findViewById<Button>(R.id.edit_profile)
        val backArrow: ImageView = findViewById(R.id.back_button)

        val nameTextView = findViewById<TextView>(R.id.username)
        val emailTextView = findViewById<TextView>(R.id.email)
        val passwordTextView = findViewById<TextView>(R.id.password)
        val contactNumberTextView = findViewById<TextView>(R.id.contact_number)
        val locationTextView = findViewById<TextView>(R.id.location)

        // Load data from SharedPreferences
        val profileData = getCurrentProfileData()

        // Set the data into the UI
        nameTextView.text = profileData.name ?: "null"
        emailTextView.text = profileData.email ?: "null"
        passwordTextView.text = profileData.password ?: "null"
        contactNumberTextView.text = profileData.contact ?: "null"
        locationTextView.text = profileData.location ?: "null"

        backArrow.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfilePage::class.java).apply {
                putExtra("PROFILE_DATA", profileData)
            }
            startActivityForResult(intent, 1001)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun getCurrentProfileData(): ProfileData {
        val name = sharedPreferences.getString("USERNAME", null)
        val password = sharedPreferences.getString("PASSWORD", null)
        val email = sharedPreferences.getString("EMAIL", null)
        val contact = sharedPreferences.getString("CONTACT", null)
        val location = sharedPreferences.getString("LOCATION", null)
        val uriString = sharedPreferences.getString("PROFILE_IMAGE_URI", null)
        val uri = uriString?.let { Uri.parse(it) }

        return ProfileData(name, password, email, contact, location, uri)
    }
}
