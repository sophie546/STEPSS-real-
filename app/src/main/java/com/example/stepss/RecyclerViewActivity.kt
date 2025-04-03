package com.example.stepss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stepss.data.Employee
import com.example.stepss.data.History
import com.example.stepss.helper.EmployeeRecyclerViewAdapter

class RecyclerViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)


        val listOfEmployee = listOf(
            Employee("001", "Juan", "Dela", "Cruz", R.drawable.names),
            Employee("002", "Maria", "Santos", "Reyes", R.drawable.names),
            Employee("003", "Pedro", "Lopez", "Gonzalez", R.drawable.names),
            Employee("004", "Ana", "Cortez", "Fernandez", R.drawable.names),
            Employee("005", "Carlos", "Diaz", "Martinez", R.drawable.names),
            Employee("006", "Sofia", "Ramos", "Hernandez", R.drawable.names),
            Employee("007", "Jose", "Morales", "Torres", R.drawable.names),
            Employee("008", "Isabel", "Navarro", "Vargas", R.drawable.names),
            Employee("009", "Rafael", "Gutierrez", "Mendoza", R.drawable.names),
            Employee("010", "Elena", "Jimenez", "Ortega", R.drawable.names),
            Employee("011", "Miguel", "Castro", "Rodriguez", R.drawable.names),
            Employee("012", "Camila", "Silva", "Pérez", R.drawable.names),
            Employee("013", "Luis", "Flores", "Moreno", R.drawable.names),
            Employee("014", "Valeria", "Mendoza", "Garcia", R.drawable.names),
            Employee("015", "Daniel", "Fernandez", "Santos", R.drawable.names),
            Employee("016", "Andrea", "Vega", "Lopez", R.drawable.names),
            Employee("017", "Fernando", "Cruz", "Diaz", R.drawable.names),
            Employee("018", "Natalia", "Ortega", "Ramirez", R.drawable.names),
            Employee("019", "Javier", "Gomez", "Suarez", R.drawable.names)
        )


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EmployeeRecyclerViewAdapter(
            listOfEmployee,
            onClick = {employee ->
                startActivity(
                    Intent(this,EmployeeDetailsActivity::class.java).apply{
                        putExtra("photo", employee.photo)
                        putExtra("id", employee.employeeId)
                        putExtra("firstname", employee.firstname)
                        putExtra("middlename", employee.middlename)
                        putExtra("lastname", employee.lastname)
                    }
                )

            }
        )


    }
}