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
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.achievement_custom_listview, parent, false)
            holder = ViewHolder(
                itemLogo = view.findViewById(R.id.item_logo),
                itemTitle = view.findViewById(R.id.item_title),
                itemDescription = view.findViewById(R.id.item_description)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val currentItem = listItems[position]
        holder.itemLogo.setImageResource(currentItem.imageResId)
        holder.itemTitle.text = currentItem.title
        holder.itemDescription.text = currentItem.description

        return view
    }

    private data class ViewHolder(
        val itemLogo: ImageView,
        val itemTitle: TextView,
        val itemDescription: TextView
    )
}

