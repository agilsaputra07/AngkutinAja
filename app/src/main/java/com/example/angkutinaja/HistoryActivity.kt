package com.example.angkutinaja

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.adapter.HistoryAdapter
import com.example.angkutinaja.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HistoryActivity : AppCompatActivity() {

    private val list = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val rv = findViewById<RecyclerView>(R.id.rvHistory)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = HistoryAdapter(list)

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("tasks")
            .whereEqualTo("userId", uid)
            .whereEqualTo("status", "Selesai")
            .get()
            .addOnSuccessListener {
                list.clear()
                for (d in it) list.add(d.toObject(Task::class.java))
                rv.adapter?.notifyDataSetChanged()
            }
    }
}
