package com.example.stepss


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import java.text.SimpleDateFormat
import java.util.*

class ActivityProgress : Activity() {
    private lateinit var historyContainer: LinearLayout
    private lateinit var homeButton: ImageButton
    private lateinit var progressButton: ImageButton
    private lateinit var burgerIcon: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val profileButton: ImageButton = findViewById(R.id.profile_button)
        historyContainer = findViewById(R.id.historyContainer)
        homeButton = findViewById(R.id.button_home)
        progressButton = findViewById(R.id.button_progress)
        burgerIcon = findViewById(R.id.burger_icon)
        val startButton: ImageButton = findViewById(R.id.button_start)

        // Set initial state (progress button selected)
        setSelectedButton(progressButton)

        // Add zoom effect to all buttons
        addZoomEffect(profileButton)
        addZoomEffect(homeButton)
        addZoomEffect(startButton)
        addZoomEffect(progressButton)
        addZoomEffect(burgerIcon)

        // Get current session data
        val stepCount = intent.getStringExtra("STEP_COUNT") ?: "0"
        val distance = intent.getStringExtra("DISTANCE") ?: "0"

        // Display current session
        findViewById<TextView>(R.id.tvStepCountProgress).text = "Steps: $stepCount"
        findViewById<TextView>(R.id.tvDistanceProgress).text = "Distance: $distance km"

        // Add to history (you'll need to implement proper storage)
        addToHistory(stepCount, distance)

        // Load and display history
        displayHistory()

        profileButton.setOnClickListener {
            Log.d("Navigation", "Navigating to ProfilePage")
            navigateTo(ProfilePage::class.java)
        }

        // Set click listeners for navigation
        homeButton.setOnClickListener {
            setSelectedButton(homeButton)
            navigateTo(LandingPageActivity::class.java)
            finish()
        }

        homeButton.setOnClickListener {
            setSelectedButton(homeButton)
            navigateTo(LandingPageActivity::class.java)
        }


        progressButton.setOnClickListener {
            setSelectedButton(progressButton)
            // Already on progress page
        }

        startButton.setOnClickListener {
            navigateTo(WalkingTrackerActivity::class.java)
        }


    }




    private fun addToHistory(steps: String, distance: String) {
        // Get current date/time
        val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
        val date = dateFormat.format(Date())

        // Store in SharedPreferences (simple example)
        val sharedPref = getSharedPreferences("StepHistory", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("last_session_$date", "Steps: $steps, Distance: $distance km")
            apply()
        }
    }

    private fun displayHistory() {
        val sharedPref = getSharedPreferences("StepHistory", MODE_PRIVATE)
        val allEntries = sharedPref.all

        // Sort entries by date (newest first)
        val sortedEntries = allEntries.entries.sortedByDescending {
            try {
                SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                    .parse(it.key.removePrefix("last_session_"))
            } catch (e: Exception) {
                Date(0)
            }
        }

        // Clear any existing views
        historyContainer.removeAllViews()

        // Display each entry with white text
        sortedEntries.forEach { entry ->
            val historyItem = TextView(this).apply {
                text = "${entry.key.removePrefix("last_session_")}: ${entry.value}"
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.white)) // Set text color to white
                setPadding(0, 8, 0, 8)
            }
            historyContainer.addView(historyItem)
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

        // Set the selected button if not null
        selectedButton?.isSelected = true
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