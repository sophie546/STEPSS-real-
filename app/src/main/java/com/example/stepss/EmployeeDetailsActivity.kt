package com.example.stepss

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//RECYCLER VIEWW SAMPLE
class EmployeeDetailsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        val imageViewPhoto = findViewById<ImageView>(R.id.imageview_photo)
        val textViewId = findViewById<TextView>(R.id.textview_id_number)
        val textViewFirstname = findViewById<TextView>(R.id.textview_firstname)
        val textViewMiddlename = findViewById<TextView>(R.id.textview_middlename)
        val textViewLastname = findViewById<TextView>(R.id.textview_lastname)

        intent?.let{
            it?.getIntExtra("photo", R.drawable.android)?.let{photoId ->
                imageViewPhoto.setImageResource(photoId)
            }

            it?.getStringExtra("id")?.let{empId ->
                textViewId.setText(empId)
            }

            it?.getStringExtra("firstname")?.let{firstname ->
                textViewFirstname.setText(firstname)
            }

            it?.getStringExtra("middlename")?.let{middlename ->
                textViewMiddlename.setText(middlename)
            }

            it?.getStringExtra("lastname")?.let{lastname ->
                textViewLastname.setText(lastname)
            }
        }
    }
}