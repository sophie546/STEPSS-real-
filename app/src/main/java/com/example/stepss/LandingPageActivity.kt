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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)

        val gifImageView = findViewById<ImageView>(R.id.gifImageView)

        Glide.with(this)
            .asGif()
            .load(R.drawable.water)
            .into(gifImageView)


        val profileButton: ImageButton = findViewById(R.id.profile_button)
        burgerIcon = findViewById(R.id.burger_icon)

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
            Toast.makeText(this, "Profile (Sophia's Page)!", Toast.LENGTH_LONG).show()
        }

        val homeButton: ImageButton = findViewById(R.id.button_home)
        val startButton: ImageButton = findViewById(R.id.button_start)
        val recoveryButton: ImageButton = findViewById(R.id.button_recovery)
        val musicButton: ImageButton = findViewById(R.id.button_music)
        val progressButton: ImageButton = findViewById(R.id.button_progress)

        homeButton.setOnClickListener{
            Toast.makeText(this,"Already here :>", Toast.LENGTH_LONG).show()
        }
        startButton.setOnClickListener{
            Toast.makeText(this,"Stay Tuned!", Toast.LENGTH_LONG).show()
        }
        recoveryButton.setOnClickListener{
            Toast.makeText(this,"Stay Tuned!", Toast.LENGTH_LONG).show()
        }
        musicButton.setOnClickListener{
            Toast.makeText(this,"Stay Tuned!", Toast.LENGTH_LONG).show()
        }
        progressButton.setOnClickListener{
            Toast.makeText(this,"Stay Tuned!", Toast.LENGTH_LONG).show()
        }

        burgerIcon.setOnClickListener { view ->
            showPopupMenu(view)
        }
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


}
