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
    import com.bumptech.glide.Glide

    class LandingPageActivity : Activity() {
        private lateinit var burgerIcon: ImageButton
        private lateinit var homeButton: ImageButton
        private lateinit var progressButton: ImageButton

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
            progressButton = findViewById(R.id.button_progress)

            // Set initial state (home selected by default)
            setSelectedButton(homeButton)

            // Add zoom effect to all buttons
            addZoomEffect(profileButton)
            addZoomEffect(burgerIcon)
            addZoomEffect(homeButton)
            addZoomEffect(startButton)
            addZoomEffect(progressButton)

            // Set click listeners
            profileButton.setOnClickListener {
                navigateTo(ProfilePage::class.java)
            }

            homeButton.setOnClickListener {
                setSelectedButton(homeButton)
            }

            progressButton.setOnClickListener {
                setSelectedButton(progressButton)
                // Replace with actual navigation when ready:
                // navigateTo(ProgressActivity::class.java)
            }
            startButton.setOnClickListener {
                // Proper navigation to WalkingTrackerActivity
                navigateTo(WalkingTrackerActivity::class.java)
            }

            burgerIcon.setOnClickListener { view ->
                showPopupMenu(view)
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

        private fun setSelectedButton(selectedButton: ImageButton) {
            // Reset all navigation buttons
            homeButton.isSelected = false
            progressButton.isSelected = false

            // Set the selected button
            selectedButton.isSelected = true
        }

        private fun showPopupMenu(view: View) {
            // Use ContextThemeWrapper to ensure proper theme
            val wrapper = ContextThemeWrapper(this, R.style.Theme_STEPSS)
            val popupMenu = PopupMenu(wrapper, view)

            popupMenu.menuInflater.inflate(R.menu.burger_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.nav_settings -> {
                        navigateTo(SettingsActivity::class.java)
                        true
                    }
                    R.id.nav_about -> {
                        navigateTo(DevelopersActivity::class.java)
                        true
                    }
                    else -> false
                }
            }

            // Force show icons if needed
            try {
                val field = popupMenu.javaClass.getDeclaredField("mPopup")
                field.isAccessible = true
                val menuPopupHelper = field.get(popupMenu) as? androidx.appcompat.view.menu.MenuPopupHelper
                menuPopupHelper?.setForceShowIcon(true)
            } catch (e: Exception) {
                e.printStackTrace()
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