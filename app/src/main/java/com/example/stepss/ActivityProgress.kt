package com.example.stepss

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class ActivityProgress : Activity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ProgressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        // Initialize views
        listView = findViewById(R.id.historyListView)

        val stepText: TextView = findViewById(R.id.tvStepCountProgress)
        val distanceText: TextView = findViewById(R.id.tvDistanceProgress)

        // Simulated values (replace with real tracking data if available)
        stepText.text = "Steps: 1234"
        distanceText.text = "Distance: 1.2 km"

        // Sample data for testing
        val historyList = listOf(
            ProgressData("2024-10-30", 1000, 0.8),
            ProgressData("2024-10-29", 2000, 1.6),
            ProgressData("2024-10-28", 1500, 1.2)
        )

        // Set adapter
        adapter = ProgressAdapter(this, historyList)
        listView.adapter = adapter
    }
}
