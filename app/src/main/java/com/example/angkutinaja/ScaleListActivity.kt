package com.example.angkutinaja

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.angkutinaja.adapter.ScaleAdapter
import com.example.angkutinaja.databinding.ActivityScaleListBinding
import com.example.angkutinaja.model.Task
import com.google.firebase.firestore.FirebaseFirestore

class ScaleListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScaleListBinding
    private val db = FirebaseFirestore.getInstance()
    private val listTask = mutableListOf<Task>()
    private lateinit var adapter: ScaleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScaleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ScaleAdapter(listTask) { task ->
            val intent = Intent(this, ScaleFormActivity::class.java)
            intent.putExtra("TASK_ID", task.id)
            intent.putExtra("NAMA", task.nama)
            intent.putExtra("JENIS", task.jenisSampah)
            startActivity(intent)
        }

        binding.rvScale.layoutManager = LinearLayoutManager(this)
        binding.rvScale.adapter = adapter

        ambilData()
    }

    private fun ambilData() {
        db.collection("tasks")
            .whereEqualTo("status", "Menunggu")
            .get()
            .addOnSuccessListener { result ->
                listTask.clear()
                for (doc in result) {
                    val task = Task(
                        id = doc.id,
                        nama = doc.getString("nama") ?: "",
                        jenisSampah = doc.getString("jenisSampah") ?: "",
                        tanggal = doc.getString("tanggal") ?: "",
                        status = doc.getString("status") ?: ""
                    )
                    listTask.add(task)
                }
                adapter.notifyDataSetChanged()
            }
    }
}
