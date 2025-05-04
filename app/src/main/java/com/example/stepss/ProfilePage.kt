package com.example.stepss

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ProfilePage : AppCompatActivity() {
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)
        Log.d("ProfilePage", "Activity created")

        sharedPreferences = getSharedPreferences("profile_prefs", MODE_PRIVATE)

        // Initialize views
        textViewName = findViewById(R.id.edit_name)
        textViewEmail = findViewById(R.id.edit_email)
        textViewTitle = findViewById(R.id.edit_title)
        textViewAddress = findViewById(R.id.edit_location)
        profileImageView = findViewById(R.id.profile_image)

        // Set default profile image
        profileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.aloria))

        // Load saved profile data
        loadProfileData()

        val backArrow: ImageView = findViewById(R.id.back_button)
        backArrow.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val logoutButton: Button = findViewById(R.id.logout_button)
        val editProfile: TextView = findViewById(R.id.edit_profile)

        logoutButton.setOnClickListener {
            showLogoutConfirmation()
        }

        editProfile.setOnClickListener {
            val intent = Intent(this, EditProfilePage::class.java).apply {
                putExtra("CURRENT_NAME", textViewName.text.toString())
                putExtra("CURRENT_EMAIL", textViewEmail.text.toString())
                putExtra("CURRENT_TITLE", textViewTitle.text.toString())
                putExtra("CURRENT_ADDRESS", textViewAddress.text.toString())
            }
            startActivityForResult(intent, 1001)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun loadProfileData() {
        textViewName.text = sharedPreferences.getString("profile_name", "Default Name")
        textViewEmail.text = sharedPreferences.getString("profile_email", "default@example.com")
        textViewTitle.text = sharedPreferences.getString("profile_title", "No title set")
        textViewAddress.text = sharedPreferences.getString("profile_address", "No address set")

        sharedPreferences.getString("profile_image_uri", null)?.let { uriString ->
            try {
                profileImageView.setImageURI(Uri.parse(uriString))
            } catch (e: Exception) {
                Log.e("ProfilePage", "Error loading profile image", e)
                profileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.aloria))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            data?.let {
                it.getStringExtra("NEW_NAME")?.let { name ->
                    textViewName.text = name
                    sharedPreferences.edit().putString("profile_name", name).apply()
                }
                it.getStringExtra("NEW_EMAIL")?.let { email ->
                    textViewEmail.text = email
                    sharedPreferences.edit().putString("profile_email", email).apply()
                }
                it.getStringExtra("NEW_TITLE")?.let { title ->
                    textViewTitle.text = title
                    sharedPreferences.edit().putString("profile_title", title).apply()
                }
                it.getStringExtra("NEW_ADDRESS")?.let { address ->
                    textViewAddress.text = address
                    sharedPreferences.edit().putString("profile_address", address).apply()
                }
                it.getStringExtra("PROFILE_IMAGE_URI")?.let { uriString ->
                    try {
                        profileImageView.setImageURI(Uri.parse(uriString))
                        sharedPreferences.edit().putString("profile_image_uri", uriString).apply()
                    } catch (e: Exception) {
                        Log.e("ProfilePage", "Error setting profile image", e)
                    }
                }
            }
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                sharedPreferences.edit().clear().apply()
                Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                }
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