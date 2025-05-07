package com.example.stepss

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stepss.data.ListItem
import com.example.stepss.helper.AchievementCustomListViewAdapter

class LandingPageActivity : AppCompatActivity() {
    private lateinit var settingsButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var progressButton: ImageButton
    private lateinit var usernameTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Setup animated GIF
        val gifImageView = findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this).asGif().load(R.drawable.water).into(gifImageView)

        // Initialize buttons
        val profileButton = findViewById<ImageButton>(R.id.profile_button)
        val startButton = findViewById<ImageButton>(R.id.button_start)
        settingsButton = findViewById(R.id.icon_settings)
        homeButton = findViewById(R.id.button_home)
        progressButton = findViewById(R.id.button_progress)

        // Initialize username text view
        usernameTextView = findViewById(R.id.username)

        // Update username display
        updateUsernameDisplay()

        // Set default button state
        setSelectedButton(homeButton)

        // Setup navigation with animations
        profileButton.setOnClickListener {
            navigateTo(ProfilePage::class.java)
            addZoomEffect(profileButton)
        }
        startButton.setOnClickListener {
            navigateTo(WalkingTrackerActivity::class.java)
            addZoomEffect(startButton)
        }
        settingsButton.setOnClickListener {
            navigateTo(SettingsActivity::class.java)
            addZoomEffect(settingsButton)
        }
        progressButton.setOnClickListener {
            setSelectedButton(progressButton)
            navigateTo(ActivityProgress::class.java)
            addZoomEffect(progressButton)
        }
        homeButton.setOnClickListener {
            setSelectedButton(homeButton)
            Toast.makeText(this, "Already on home page", Toast.LENGTH_SHORT).show()
        }

        // Set up custom list view for achievements
        setupAchievementsList()

        sharedPreferences = getSharedPreferences("StepHistory", MODE_PRIVATE)

        val mostRecentSteps = sharedPreferences.getString("last_steps", "0")
        val mostRecentDistance = sharedPreferences.getString("last_distance", "0")

        // Update the UI with the most recent data
        val distanceTextView = findViewById<TextView>(R.id.txt_distance)
        val stepsTextView = findViewById<TextView>(R.id.txt_steps)

        distanceTextView.text = "$mostRecentDistance"
        stepsTextView.text = "$mostRecentSteps"
    }

    override fun onResume() {
        super.onResume()
        // Update username display when returning to this activity
        updateUsernameDisplay()
    }

    private fun updateUsernameDisplay() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", "")
        usernameTextView.text = "$savedUsername!"
        Log.d("LandingPage", "Updated username display: $savedUsername")
    }

    private fun setupAchievementsList() {
        val listView = findViewById<ListView>(R.id.list_view)
        val listItems = listOf(
            ListItem(R.drawable.icon_medal, "10,000 Steps", "Complete 10,000 steps in a single day"),
            ListItem(R.drawable.icon_walking, "Daily Walker", "Walk every day for a week."),
            ListItem(R.drawable.icon_globe, "Around the World", "Walk a total of 40,000 kilometers."),
            ListItem(R.drawable.icon_flame, "Calories Burned", "Burn 500 calories in a single walk."),
            ListItem(R.drawable.icon_target, "Goal Getter", "Achieve your daily step goal for 7 days.")
        )
        val adapter = AchievementCustomListViewAdapter(this, listItems)
        listView.adapter = adapter
    }

    private fun navigateTo(targetClass: Class<*>) {
        startActivity(Intent(this, targetClass))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun setSelectedButton(selectedButton: ImageButton) {
        homeButton.isSelected = false
        progressButton.isSelected = false
        selectedButton.isSelected = true
    }

    private fun addZoomEffect(button: ImageButton) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).start()
                }
                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                    v.performClick()
                }
            }
            true
        }
    }
}
