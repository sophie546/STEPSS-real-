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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var profileImageView: ImageView
    private lateinit var profileNameTextView: TextView
    private lateinit var profileEmailTextView: TextView

    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            data?.let {
                it.getStringExtra("NEW_NAME")?.let { name ->
                    profileNameTextView.text = name
                    sharedPreferences.edit().putString("profile_name", name).apply()
                }
                it.getStringExtra("NEW_EMAIL")?.let { email ->
                    profileEmailTextView.text = email
                    sharedPreferences.edit().putString("profile_email", email).apply()
                }
                it.getStringExtra("PROFILE_IMAGE_URI")?.let { uriString ->
                    try {
                        profileImageView.setImageURI(Uri.parse(uriString))
                        sharedPreferences.edit().putString("profile_image_uri", uriString).apply()
                    } catch (e: Exception) {
                        Log.e("SettingsActivity", "Error setting profile image", e)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)
        Log.d("SettingsActivity", "Activity created")

        sharedPreferences = getSharedPreferences("profile_prefs", MODE_PRIVATE)

        // Initialize views
        profileImageView = findViewById(R.id.profile_image)
        profileNameTextView = findViewById(R.id.profile_name)
        profileEmailTextView = findViewById(R.id.profile_email)


        loadProfileData()

        // Initialize other views
        val notificationsOption = findViewById<LinearLayout>(R.id.option_notifications)
        val logoutOption = findViewById<Button>(R.id.button_logout_button)
        val backArrow = findViewById<LinearLayout>(R.id.back_arrow)
        val aboutDevs = findViewById<LinearLayout>(R.id.option_about_devs)
        val historyOption = findViewById<LinearLayout>(R.id.option_history_container)
        val profileSection = findViewById<LinearLayout>(R.id.profile_section)

        // Set click listeners
        notificationsOption.setOnClickListener { showNotificationPermissionDialog() }
        historyOption.setOnClickListener { navigateTo(CustomListViewActivity::class.java) }
        logoutOption.setOnClickListener { showLogoutConfirmation() }
        backArrow.setOnClickListener { finishWithAnimation() }
        aboutDevs.setOnClickListener { navigateTo(DevelopersActivity::class.java) }
        profileSection.setOnClickListener { openEditProfile() }
    }

    private fun loadProfileData() {
        profileNameTextView.text = sharedPreferences.getString("profile_name", "Default Name")
        profileEmailTextView.text = sharedPreferences.getString("profile_email", "default@example.com")

        sharedPreferences.getString("profile_image_uri", null)?.let { uriString ->
            try {
                profileImageView.setImageURI(Uri.parse(uriString))
            } catch (e: Exception) {
                Log.e("SettingsActivity", "Error loading profile image", e)
            }
        }
    }

    private fun openEditProfile() {
        Intent(this, EditProfilePage::class.java).apply {
            putExtra("CURRENT_NAME", profileNameTextView.text.toString())
            putExtra("CURRENT_EMAIL", profileEmailTextView.text.toString())
            editProfileLauncher.launch(this)
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun showNotificationPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Allow Notifications?")
            .setMessage("Would you like to enable notifications for this app?")
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, "Notifications Enabled", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putBoolean("notifications_enabled", true).apply()
            }
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(this, "Notifications Disabled", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putBoolean("notifications_enabled", false).apply()
            }
            .show()
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

    private fun navigateTo(targetClass: Class<*>) {
        try {
            val intent = Intent(this, targetClass)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("Navigation", "Error navigating to ${targetClass.simpleName}", e)
        }
    }

    private fun finishWithAnimation() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        finishWithAnimation()
    }
}