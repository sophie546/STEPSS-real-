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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)
        Log.d("SettingsActivity", "Activity created")

        sharedPreferences = getSharedPreferences("profile_prefs", MODE_PRIVATE)


        // Initialize other views
        val notificationsOption = findViewById<LinearLayout>(R.id.option_notifications)
        val logoutOption = findViewById<Button>(R.id.button_logout_button)
        val backArrow = findViewById<LinearLayout>(R.id.back_arrow)
        val aboutDevs = findViewById<LinearLayout>(R.id.option_about_devs)
        val progressActivity = findViewById<LinearLayout>(R.id.option_history_container)

        notificationsOption.setOnClickListener { showNotificationPermissionDialog() }
        progressActivity.setOnClickListener { navigateTo(ActivityProgress::class.java) }
        logoutOption.setOnClickListener { showLogoutConfirmation() }
        backArrow.setOnClickListener { finishWithAnimation() }
        aboutDevs.setOnClickListener { navigateTo(DevelopersActivity::class.java) }
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