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

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var contactNumberTextView: TextView
    private lateinit var locationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page2)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val editProfileButton = findViewById<Button>(R.id.edit_profile)
        val backArrow: ImageView = findViewById(R.id.back_button)

        nameTextView = findViewById(R.id.username)
        emailTextView = findViewById(R.id.email)
        passwordTextView = findViewById(R.id.password)
        contactNumberTextView = findViewById(R.id.contact_number)
        locationTextView = findViewById(R.id.location)

        // Load and display current profile data
        val profileData = getCurrentProfileData()
        setProfileDataToUI(profileData)

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

    private fun setProfileDataToUI(profileData: ProfileData) {
        nameTextView.text = profileData.name ?: "null"
        emailTextView.text = profileData.email ?: "null"
        passwordTextView.text = profileData.password ?: "null"
        contactNumberTextView.text = profileData.contact ?: "null"
        locationTextView.text = profileData.location ?: "null"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            val updatedProfileData = data.getParcelableExtra<ProfileData>("UPDATED_PROFILE_DATA")
            if (updatedProfileData != null) {
                // Update the UI
                setProfileDataToUI(updatedProfileData)

                // Save updated data to SharedPreferences
                with(sharedPreferences.edit()) {
                    putString("USERNAME", updatedProfileData.name)
                    putString("PASSWORD", updatedProfileData.password)
                    putString("EMAIL", updatedProfileData.email)
                    putString("CONTACT", updatedProfileData.contact)
                    putString("LOCATION", updatedProfileData.location)
                    putString("PROFILE_IMAGE_URI", updatedProfileData.imageUri?.toString())
                    apply()
                }
            }
        }
    }
}
