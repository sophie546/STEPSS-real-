//package com.example.stepss.helper
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.ImageView
//import android.widget.TextView
//import com.example.stepss.R
//import com.example.stepss.data.History
//
//class HistoryCustomListViewAdapter(
//    private val context: Context,
//    private  val historyList: List<History>,
//    private val onClick: (History)  -> Unit,
//    private val onLongClick: (History) -> Unit
//    ): BaseAdapter() {
//    override fun getCount(): Int = historyList.size
//
//    override fun getItem(position: Int): Any = historyList[position]
//
//    override fun getItemId(position: Int): Long = position.toLong()
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view = convertView ?: LayoutInflater.from(context)
//            .inflate(R.layout.achievement_custom_listview, parent, false)
//
//        val profilPic = view.findViewById<ImageView>(R.id.imageview_profile_pic)
//        val fullname = view.findViewById<TextView>(R.id.textview_fullname)
//
//        val history = historyList[position]
//
//        profilPic.setImageResource(history.photoRes)
//        fullname.setText("${history.date}, ${history.location}, ${history.time}")
//
//
//        view.setOnClickListener {
//            onClick(history)
//        }
//
//        view.setOnLongClickListener {
//            onLongClick(history)
//            true
//        }
//        return view
//    }
//
//
//}