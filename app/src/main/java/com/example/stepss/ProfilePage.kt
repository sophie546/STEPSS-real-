package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.stepss.EditProfilePage
import com.example.stepss.LoginActivity
import com.example.stepss.ProfilePage2
import com.example.stepss.R

class ProfilePage : AppCompatActivity() {
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)
        Log.d("ProfilePage", "Activity created")

        sharedPreferences = getSharedPreferences("profile_prefs", MODE_PRIVATE)

        // Initialize views
        textViewName = findViewById(R.id.name)
        textViewEmail = findViewById(R.id.email_address)
        profileImageView = findViewById(R.id.profile_image)

        val logoutButton: Button = findViewById(R.id.logout_button)
        val editProfile: LinearLayout = findViewById(R.id.edit_profile_button)
        val showProfile: LinearLayout = findViewById(R.id.profile_header)
        val backArrow: ImageView = findViewById(R.id.back_button)

        // Set default profile image
        profileImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.aloria))

        loadProfileData()

        backArrow.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        logoutButton.setOnClickListener {
            showLogoutConfirmation()
        }

        editProfile.setOnClickListener {
            val intent = Intent(this, EditProfilePage::class.java)
            intent.putExtra("CURRENT_NAME", textViewName.text.toString())
            intent.putExtra("CURRENT_EMAIL", textViewEmail.text.toString())
            startActivityForResult(intent, 1001)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        showProfile.setOnClickListener {
            val intent = Intent(this, ProfilePage2::class.java)
            intent.putExtra("CURRENT_NAME", textViewName.text.toString())
            intent.putExtra("CURRENT_EMAIL", textViewEmail.text.toString())
            startActivityForResult(intent, 1001)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun loadProfileData() {
        val username = sharedPreferences.getString("profile_name", "Default Name")
        val email = sharedPreferences.getString("profile_email", "")

        textViewName.text = username ?: "Default Name"

        // Display email only if it's not empty
        textViewEmail.text = if (!email.isNullOrEmpty()) email else ""

        // Load profile image if available
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
