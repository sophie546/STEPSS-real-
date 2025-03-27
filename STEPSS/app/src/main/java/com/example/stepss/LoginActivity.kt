package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val buttonRegister:Button = findViewById<Button>(R.id.login)
        buttonRegister.setOnClickListener{
            Toast.makeText(this, "Button is clicked!", Toast.LENGTH_LONG).show()
            val intent = Intent(this,Activity_profiles_page::class.java)
            startActivity(intent)
        }

    }
}