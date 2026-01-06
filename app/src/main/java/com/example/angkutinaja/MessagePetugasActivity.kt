package com.example.angkutinaja

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MessagePetugasActivity : AppCompatActivity() {

    private lateinit var rvMessage: RecyclerView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_petugas)

        rvMessage = findViewById(R.id.rvMessage)
        rvMessage.layoutManager = LinearLayoutManager(this)

        ambilDataMessage()
    }

    private fun ambilDataMessage() {
        db.collection("tasks")
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    rvMessage.adapter =
                        MessageAdapter(this, result.documents)
                } else {
                    Toast.makeText(this, "Belum ada data", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
    }
}
