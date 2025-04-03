package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button


class DevelopersActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developers_page)

        // Home Button
        val buttonHome: Button = findViewById(R.id.btn_home) // Ensure this ID exists in XML
        buttonHome.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears backstack
            startActivity(intent)
            finish()
        }
    }
}
