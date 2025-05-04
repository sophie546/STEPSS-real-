package com.example.stepss

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ProfilePage : Activity() {
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewAddress: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        // Initialize text views
        textViewName = findViewById(R.id.edit_name)
        textViewEmail = findViewById(R.id.edit_email)
        textViewTitle = findViewById(R.id.edit_title)
        textViewAddress = findViewById(R.id.edit_location)

        //error here
        val backArrow: ImageView = findViewById(R.id.back_button)
        backArrow.setOnClickListener{
            val intent = Intent(this,LandingPageActivity::class.java)
            startActivity(intent)
        }

        val logoutButton: Button = findViewById(R.id.logout_button)
        val editProfile: ImageView = findViewById(R.id.edit_profile)

        // Handle Logout Click
        logoutButton.setOnClickListener {
            showLogoutConfirmation()
        }

        // Handle Edit Profile Click
        editProfile.setOnClickListener {
            val intent = Intent(this, EditProfilePage::class.java)
            startActivityForResult(intent, 1001) // Start Edit Profile Activity
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val newName = data?.getStringExtra("NEW_NAME") ?: ""
            val newEmail = data?.getStringExtra("NEW_EMAIL") ?: ""
            val newTitle = data?.getStringExtra("NEW_TITLE") ?: ""
            val newAddress = data?.getStringExtra("NEW_ADDRESS") ?: ""

            // Update TextViews with new values
            textViewName.text = newName
            textViewEmail.text = newEmail
            textViewTitle.text = newTitle
            textViewAddress.text = newAddress
        }
    }

    private fun showLogoutConfirmation() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out?")

        builder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears backstack
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}
