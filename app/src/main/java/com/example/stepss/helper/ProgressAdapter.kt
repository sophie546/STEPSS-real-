package com.example.stepss

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProgressAdapter(
    private val context: Activity,
    private val dataList: List<ProgressData>
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false)

        val dateTextView: TextView = view.findViewById(R.id.tvDate)
        val stepsTextView: TextView = view.findViewById(R.id.tvSteps)
        val distanceTextView: TextView = view.findViewById(R.id.tvDistance)

        val item = dataList[position]

        dateTextView.text = item.date
        stepsTextView.text = "Steps: ${item.steps}"
        distanceTextView.text = "Distance: ${item.distance} km"

        return view
    }



}
