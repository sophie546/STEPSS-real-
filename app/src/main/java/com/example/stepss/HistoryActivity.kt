//package com.example.stepss
//
//import android.app.Activity
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.ListView
//
//
//class HistoryActivity : Activity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.history_page)
//
//        val historyList = listOf("March 25, 2025", "March 26, 2025", "March 27, 2025", "March 28, 2025", "March 29, 2025", "March 30, 2025", "April 1, 2025", "April 2, 2025", "April 3, 2025", "April 4, 2025", "April 5, 2025",)
//
//        //ArrayAdapter
//        val arrayAdapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            historyList
//        )
//
//        val listView = findViewById<ListView>(R.id.listview)
//        listView.adapter = arrayAdapter
//
//    }
//}