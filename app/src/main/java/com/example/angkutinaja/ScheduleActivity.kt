package com.example.angkutinaja

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.adapter.ScheduleAdapter
import com.example.angkutinaja.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ScheduleActivity : AppCompatActivity() {

    private val list = mutableListOf<Task>()
    private lateinit var adapter: ScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val rv = findViewById<RecyclerView>(R.id.rvSchedule)
        rv.layoutManager = LinearLayoutManager(this)

        adapter = ScheduleAdapter(list)
        rv.adapter = adapter

        ambilData()
    }

    private fun ambilData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("tasks")
            .whereEqualTo("userId", uid)
            .whereEqualTo("status", "Menunggu")
            .get()
            .addOnSuccessListener { result ->
                list.clear()
                for (doc in result) {
                    list.add(doc.toObject(Task::class.java))
                }
                adapter.notifyDataSetChanged()
            }
    }
}
