package com.example.stepss

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import android.view.View
import android.content.Intent
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import android.widget.ImageView


class LandingPageActivity : Activity() {
    private lateinit var burgerIcon: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var progressButton: ImageButton
    private lateinit var recoveryButton: ImageButton
    private lateinit var musicButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)

        // Initialize views
        val gifImageView = findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this).asGif().load(R.drawable.water).into(gifImageView)

        val profileButton: ImageButton = findViewById(R.id.profile_button)
        burgerIcon = findViewById(R.id.burger_icon)

        homeButton = findViewById(R.id.button_home)
        val startButton: ImageButton = findViewById(R.id.button_start)
        recoveryButton = findViewById(R.id.button_recovery)
        musicButton = findViewById(R.id.button_music)
        progressButton = findViewById(R.id.button_progress)

        // Set initial state (home selected by default)
        setSelectedButton(homeButton)

        // Add zoom effect to all buttons
        addZoomEffect(profileButton)
        addZoomEffect(burgerIcon)
        addZoomEffect(homeButton)
        addZoomEffect(startButton)
        addZoomEffect(recoveryButton)
        addZoomEffect(musicButton)
        addZoomEffect(progressButton)

        // Set click listeners
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
            Toast.makeText(this, "Profile (Sophia's Page)!", Toast.LENGTH_LONG).show()
        }

        homeButton.setOnClickListener {
            setSelectedButton(homeButton)
            Toast.makeText(this, "Already here :>", Toast.LENGTH_LONG).show()
        }

        progressButton.setOnClickListener {
            setSelectedButton(progressButton)
            Toast.makeText(this, "Stay Tuned!", Toast.LENGTH_LONG).show()
            // Replace with actual navigation when ready:
            // val intent = Intent(this, ProgressActivity::class.java)
            // startActivity(intent)
        }

        recoveryButton.setOnClickListener {
            setSelectedButton(recoveryButton)
            Toast.makeText(this, "Stay Tuned!", Toast.LENGTH_LONG).show()
        }

        musicButton.setOnClickListener {
            setSelectedButton(musicButton)
            Toast.makeText(this, "Stay Tuned!", Toast.LENGTH_LONG).show()
        }

        startButton.setOnClickListener {
            // Center button typically doesn't get highlighted
            Toast.makeText(this, "Stay Tuned!", Toast.LENGTH_LONG).show()
        }

        burgerIcon.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    private fun setSelectedButton(selectedButton: ImageButton) {
        // Reset all navigation buttons
        homeButton.isSelected = false
        progressButton.isSelected = false
        recoveryButton.isSelected = false
        musicButton.isSelected = false

        // Set the selected button
        selectedButton.isSelected = true
    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.burger_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Settings (Sophia's Page)!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_about -> {
                    val intent = Intent(this, DevelopersActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "About Us (Sophia's Page)!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_logout -> {
                    showConfirmLogoutDialog()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
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