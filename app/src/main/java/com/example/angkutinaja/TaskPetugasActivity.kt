package com.example.angkutinaja

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.adapter.TaskAdapter
import com.example.angkutinaja.model.Task
import com.google.firebase.firestore.FirebaseFirestore

class TaskPetugasActivity : AppCompatActivity() {

    private val listTask = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_petugas)

        val rv = findViewById<RecyclerView>(R.id.rvTask)
        rv.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(listTask)
        rv.adapter = adapter

        loadTask()
    }

    private fun loadTask() {
        FirebaseFirestore.getInstance()
            .collection("tasks")
            .whereEqualTo("status", "Menunggu") // 🔥 FILTER
            .get()
            .addOnSuccessListener { result ->
                listTask.clear()
                for (doc in result) {
                    val task = doc.toObject(Task::class.java)
                    task.id = doc.id // 🔥 WAJIB untuk update
                    listTask.add(task)
                }
                adapter.notifyDataSetChanged()
            }
    }

    override fun onResume() {
        super.onResume()
        loadTask() // 🔄 REFRESH setelah konfirmasi
    }
}
