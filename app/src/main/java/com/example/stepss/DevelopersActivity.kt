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

        val backButton: ImageView = findViewById(R.id.back_button)
        homeButton = findViewById(R.id.button_home)
        val startButton: ImageButton = findViewById(R.id.button_start)
        progressButton = findViewById(R.id.button_progress)

        setSelectedButton(null)

        addZoomEffect(backButton as ImageButton)
        addZoomEffect(homeButton)
        addZoomEffect(startButton)
        addZoomEffect(progressButton)

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
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun setSelectedButton(selectedButton: ImageButton?) {
        // Reset all navigation buttons
        homeButton.isSelected = false
        progressButton.isSelected = false

        selectedButton?.isSelected = true
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