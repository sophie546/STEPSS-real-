//package com.example.stepss.helper
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.stepss.R
//import com.example.stepss.data.Employee
//
//
//class EmployeeRecyclerViewAdapter(
//    private val listOfEmployees: List<Employee>,
//    private val onClick: (Employee) -> Unit
//    ) :RecyclerView.Adapter<EmployeeRecyclerViewAdapter.ItemViewHolder>() {
//        class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
//            val photo = view.findViewById<ImageView>(R.id.imageview_profile_pic)
//            val employeeId = view.findViewById<TextView>(R.id.textview_id)
//            val firstname = view.findViewById<TextView>(R.id.textview_firstname)
//            val middlename = view.findViewById<TextView>(R.id.textview_middle)
//            val lastname = view.findViewById<TextView>(R.id.textview_lastname)
//        }
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ItemViewHolder{
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.employee_item_recycler_view,parent,false)
//        return ItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(
//        holder: ItemViewHolder,
//        position: Int
//    ){
//        val item = listOfEmployees[position]
//
//        holder.photo.setImageResource(item.photo)
//        holder.employeeId.setText(item.employeeId)
//        holder.firstname.setText(item.firstname)
//        holder.middlename.setText(item.middlename)
//        holder.lastname.setText(item.lastname)
//
//        holder.itemView.setOnClickListener {
//            onClick(item)
//        }
//    }
//
//    override fun getItemCount(): Int = listOfEmployees.size
//
//}