    package com.example.stepss

    import android.os.Bundle
    import android.app.Activity
    import android.app.AlertDialog
    import android.content.Intent
    import android.widget.LinearLayout

    class activity_settings : Activity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_settings) // Ensure this matches your XML file name

            // Find the Logout option
            val logoutOption: LinearLayout = findViewById(R.id.logout_button)

            // Find the Back Arrow
            val backArrow: LinearLayout = findViewById(R.id.back_arrow)

            // Find "About the Developers" option
            val aboutDevs: LinearLayout = findViewById(R.id.option_about_devs)

            // Handle Logout Click
            logoutOption.setOnClickListener {
                showLogoutConfirmation()
            }

            // Handle Back Arrow Click
            backArrow.setOnClickListener {
                redirectToLogin()
            }

            // Handle About Developers Click
            aboutDevs.setOnClickListener {
                val intent = Intent(this, developers_page::class.java)
                startActivity(intent)
            }
        }

        private fun showLogoutConfirmation() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to log out?")

            builder.setPositiveButton("Yes") { _, _ ->
                // Redirect to LoginActivity
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

        private fun redirectToLogin() {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears backstack
            startActivity(intent)
            finish()
        }
    }
