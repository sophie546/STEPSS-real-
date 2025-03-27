package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast


class WelcomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)

        val button_register = findViewById<Button>(R.id.button_RegisterOrLogin)
        button_register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity((intent))
        }

        val Guest = findViewById<Button>(R.id.button_VisitAsGuest)
        Guest.setOnClickListener{
            Log.e("Sample Project", "Button is clicked!")
            Toast.makeText(this,"Feature not yet available :)", Toast.LENGTH_LONG).show()
//            val intent = Intent(this, LandingPageActivity::class.java)
//            startActivity((intent))
        }
    }
}