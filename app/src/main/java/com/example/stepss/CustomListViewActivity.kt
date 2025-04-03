package com.example.stepss

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.stepss.data.History
import com.example.stepss.helper.HistoryCustomListViewAdapter

class CustomListViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_page)

        val listView = findViewById<ListView>(R.id.listview)

        //FROM DATA > HISTORY (DATA TYPES)
        val listOfHistory = listOf(
            History("March 1, 2025", "Toledo City Cebu", "5:30 AM", R.drawable.names),
            History("March 2, 2025", "Cebu City", "6:30 AM", R.drawable.names),
            History("March 3, 2025", "Banawa Cebu", "8:30 AM", R.drawable.names),
            History("March 4, 2025", "Guadalupe, Salvador", "10:30 AM", R.drawable.names),
            History("March 5, 2025", "Mandaue City", "7:15 AM", R.drawable.names),
            History("March 6, 2025", "Lapu-Lapu City", "9:45 AM", R.drawable.names),
            History("March 7, 2025", "Talisay City", "12:00 PM", R.drawable.names),
            History("March 8, 2025", "Minglanilla", "2:30 PM", R.drawable.names),
            History("March 9, 2025", "Naga City, Cebu", "4:00 PM", R.drawable.names),
            History("March 10, 2025", "Carcar City", "6:20 PM", R.drawable.names),
            History("March 11, 2025", "Danao City", "7:50 AM", R.drawable.names),
            History("March 12, 2025", "Consolacion", "9:10 AM", R.drawable.names),
            History("March 13, 2025", "Liloan, Cebu", "11:00 AM", R.drawable.names),
            History("March 14, 2025", "Compostela, Cebu", "1:40 PM", R.drawable.names),
            History("March 15, 2025", "Dumanjug, Cebu", "3:30 PM", R.drawable.names),
            History("March 16, 2025", "Moalboal, Cebu", "5:45 PM", R.drawable.names),
            History("March 17, 2025", "Alegria, Cebu", "8:15 PM", R.drawable.names),
            History("March 18, 2025", "Badian, Cebu", "10:00 PM", R.drawable.names),
            History("March 19, 2025", "Oslob, Cebu", "11:30 PM", R.drawable.names)
        )

        val adapter = HistoryCustomListViewAdapter(
            this,
            listOfHistory,
            onClick = { history ->
                Toast.makeText(this, "${history.date}, ${history.location} was clicked", Toast.LENGTH_SHORT).show()
            },
            onLongClick = { history ->
                Toast.makeText(this, "${history.date}, ${history.location} was long clicked", Toast.LENGTH_SHORT).show()
            }
            )
        listView.adapter = adapter

    }
}