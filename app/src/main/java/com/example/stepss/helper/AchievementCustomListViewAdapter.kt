package com.example.stepss.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stepss.R
import com.example.stepss.data.ListItem

class AchievementCustomListViewAdapter(
    private val context: Context,
    private val listItems: List<ListItem>
) : BaseAdapter() {

    override fun getCount(): Int = listItems.size

    override fun getItem(position: Int): Any = listItems[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.achievement_custom_listview, parent, false)

        val itemLogo: ImageView = view.findViewById(R.id.item_logo)
        val itemTitle: TextView = view.findViewById(R.id.item_title)
        val itemDescription: TextView = view.findViewById(R.id.item_description)

        val currentItem = listItems[position]

        itemLogo.setImageResource(currentItem.imageResId)
        itemTitle.text = currentItem.title
        itemDescription.text = currentItem.description

        return view
    }
}
