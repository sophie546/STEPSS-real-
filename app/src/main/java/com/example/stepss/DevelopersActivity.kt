package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu

class DevelopersActivity : Activity() {
    private lateinit var homeButton: ImageButton
    private lateinit var progressButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developers_page)

        // Initialize views
        val backButton: ImageView = findViewById(R.id.back_button)
        homeButton = findViewById(R.id.button_home)
        val startButton: ImageButton = findViewById(R.id.button_start)
        progressButton = findViewById(R.id.button_progress)

        // Set initial state (none selected as this isn't a main navigation page)
        setSelectedButton(null)

        // Add zoom effect to all buttons
        addZoomEffect(backButton as ImageButton)
        addZoomEffect(homeButton)
        addZoomEffect(startButton)
        addZoomEffect(progressButton)

        // Set click listeners
        backButton.setOnClickListener {
            navigateTo(LandingPageActivity::class.java)
            finish()
        }

        homeButton.setOnClickListener {
            setSelectedButton(homeButton)
            navigateTo(LandingPageActivity::class.java)
            finish()
        }

        progressButton.setOnClickListener {
            setSelectedButton(progressButton)
            navigateTo(ActivityProgress::class.java)
        }

        startButton.setOnClickListener {
            navigateTo(WalkingTrackerActivity::class.java)
        }


    }

    private fun navigateTo(targetClass: Class<*>) {
        val intent = Intent(this, targetClass)
        // Clear any flags that might cause issues
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        // Optional: Add animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun setSelectedButton(selectedButton: ImageButton?) {
        // Reset all navigation buttons
        homeButton.isSelected = false
        progressButton.isSelected = false

        // Set the selected button if not null
        selectedButton?.isSelected = true
    }

    private fun showPopupMenu(view: View) {
        // Use ContextThemeWrapper to ensure proper theme
        val wrapper = ContextThemeWrapper(this, R.style.Theme_STEPSS)
        val popupMenu = PopupMenu(wrapper, view)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_settings -> {
                    navigateTo(SettingsActivity::class.java)
                    true
                }
                R.id.nav_about -> {
                    // Already on developers page
                    true
                }
                else -> false
            }
        }
    }

    private fun showConfirmLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun addZoomEffect(button: ImageButton) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).start()
                }
                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                }
            }
            false
        }
    }
}