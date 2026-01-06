package com.example.angkutinaja

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.adapter.ScaleAdapter
import com.example.angkutinaja.model.Task
import com.google.firebase.firestore.FirebaseFirestore

class ScaleActivity : AppCompatActivity() {

    private lateinit var rvScale: RecyclerView
    private val taskList = mutableListOf<Task>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale)

        rvScale = findViewById(R.id.rvScale)
        rvScale.layoutManager = LinearLayoutManager(this)

        ambilDataTask()
    }

    private fun ambilDataTask() {
        db.collection("tasks")
            .whereEqualTo("status", "Menunggu")
            .get()
            .addOnSuccessListener { result ->
                taskList.clear()
                for (doc in result) {
                    taskList.add(
                        Task(
                            id = doc.id,
                            nama = doc.getString("nama") ?: "",
                            jenisSampah = doc.getString("jenisSampah") ?: ""
                        )
                    )
                }

                rvScale.adapter = ScaleAdapter(taskList) { task ->
                    val intent = Intent(this, ScaleDetailActivity::class.java)
                    intent.putExtra("TASK_ID", task.id)
                    intent.putExtra("NAMA", task.nama)
                    intent.putExtra("JENIS", task.jenisSampah)
                    startActivity(intent)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        ambilDataTask()
    }
}
