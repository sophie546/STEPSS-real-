package com.example.stepss

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stepss.data.ListItem
import com.example.stepss.helper.AchievementCustomListViewAdapter

class LandingPageActivity : AppCompatActivity() {
    private lateinit var settingsButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var progressButton: ImageButton
    private lateinit var usernameTextView: TextView
    private lateinit var ageCountryTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)

        // Initialize views
        val gifImageView = findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this).asGif().load(R.drawable.water).into(gifImageView)

        val profileButton: ImageButton = findViewById(R.id.profile_button)
        val startButton: ImageButton = findViewById(R.id.button_start)

        settingsButton = findViewById(R.id.icon_settings)
        homeButton = findViewById(R.id.button_home)
        progressButton = findViewById(R.id.button_progress)

        // Initialize TextViews
        usernameTextView = findViewById(R.id.username)
        ageCountryTextView = findViewById(R.id.age_country)

        // Set initial state (home selected by default)
        setSelectedButton(homeButton)

        // Set click listeners for buttons
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
        }


        homeButton.setOnClickListener {
            setSelectedButton(homeButton)
            Toast.makeText(this, "Already on home page", Toast.LENGTH_SHORT).show()
        }

        progressButton.setOnClickListener {
            setSelectedButton(progressButton)
            navigateTo(ActivityProgress::class.java)
        }

        startButton.setOnClickListener {
            navigateTo(WalkingTrackerActivity::class.java)
        }

        settingsButton.setOnClickListener {
            navigateTo(SettingsActivity::class.java)
        }

        // Retrieve username, age, and location from SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", "Unknown User")
        val savedAge = ""  // Age is set to empty string
        val savedLocation = ""  // Location is set to empty string

        // Update UI with the data
        usernameTextView.text = savedUsername
        if(savedAge.isEmpty() || savedLocation.isEmpty()){
            ageCountryTextView.text = "$savedAge  $savedLocation"
        }else{
            ageCountryTextView.text = "$savedAge yrs · $savedLocation"
        }


        //CUSTOM LIST VIEW...
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
        try {
            val intent = Intent(this, targetClass)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
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








