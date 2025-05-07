package com.example.stepss

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.stepss.data.ProfileData

class ProfilePage : AppCompatActivity() {
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)
        Log.d("ProfilePage", "Activity created")

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        textViewName = findViewById(R.id.name)
        textViewEmail = findViewById(R.id.email_address)
        profileImageView = findViewById(R.id.profile_image)

        val logoutButton: Button = findViewById(R.id.logout_button)
        val editProfile: LinearLayout = findViewById(R.id.edit_profile_button)
        val showProfile: LinearLayout = findViewById(R.id.profile_header)
        val backArrow: ImageView = findViewById(R.id.back_button)

        profileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_picture))

        loadProfileData()

        backArrow.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        logoutButton.setOnClickListener {
            showLogoutConfirmation()
        }

        editProfile.setOnClickListener {
            val profileData = getCurrentProfileData()
            val intent = Intent(this, EditProfilePage::class.java)
            intent.putExtra("PROFILE_DATA", profileData)
            startActivityForResult(intent, 1001)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        showProfile.setOnClickListener {
            val profileData = getCurrentProfileData()
            val intent = Intent(this, ProfilePage2::class.java)
            intent.putExtra("PROFILE_DATA", profileData)
            startActivityForResult(intent, 1001)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun loadProfileData() {
        val username = sharedPreferences.getString("USERNAME", null)
        val email = sharedPreferences.getString("EMAIL", null)
        val imageUriString = sharedPreferences.getString("PROFILE_IMAGE_URI", null)

        textViewName.text = username ?: ""
        textViewEmail.text = email ?: ""

        try {
            if (!imageUriString.isNullOrEmpty()) {
                profileImageView.setImageURI(Uri.parse(imageUriString))
            } else {
                profileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_picture))
            }
        } catch (e: Exception) {
            Log.e("ProfilePage", "Error loading profile image", e)
            profileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_picture))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            data?.let {
                val updatedProfile = it.getParcelableExtra<ProfileData>("UPDATED_PROFILE_DATA")
                Log.d("ProfilePage", "Updated profile data: $updatedProfile")
                updatedProfile?.let { profile ->
                    // Update UI immediately with the updated profile data
                    textViewName.text = profile.name ?: ""
                    textViewEmail.text = profile.email ?: ""

                    // Update profile image if changed
                    profile.imageUri?.let { uri ->
                        try {
                            profileImageView.setImageURI(uri)
                        } catch (e: Exception) {
                            Log.e("ProfilePage", "Error setting profile image", e)
                            profileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_picture))
                        }
                    }

                    // Save the updated profile data to SharedPreferences
                    with(sharedPreferences.edit()) {
                        putString("USERNAME", profile.name)
                        putString("EMAIL", profile.email)
                        profile.imageUri?.let { uri ->
                            putString("PROFILE_IMAGE_URI", uri.toString())
                        }
                        apply()
                    }
                }
            }
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

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                sharedPreferences.edit().clear().apply()
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
