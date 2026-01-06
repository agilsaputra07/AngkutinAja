package com.example.angkutinaja

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.angkutinaja.databinding.ActivityScaleFormBinding
import com.google.firebase.firestore.FirebaseFirestore

class ScaleFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScaleFormBinding
    private val db = FirebaseFirestore.getInstance()
    private val hargaPerKg = 3000
    private var taskId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScaleFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskId = intent.getStringExtra("TASK_ID") ?: ""

        binding.etNama.setText(intent.getStringExtra("NAMA"))
        binding.etJenis.setText(intent.getStringExtra("JENIS"))

        binding.btnKonfirmasi.setOnClickListener {
            konfirmasi()
        }
    }

    private fun konfirmasi() {
        val beratText = binding.etBerat.text.toString()

        if (beratText.isEmpty()) {
            Toast.makeText(this, "Masukkan berat", Toast.LENGTH_SHORT).show()
            return
        }

        val berat = beratText.toDouble()
        val total = berat * hargaPerKg

        binding.tvTotal.text = "Total Harga: Rp ${total.toInt()}"

        val history = hashMapOf(
            "nama" to binding.etNama.text.toString(),
            "jenisSampah" to binding.etJenis.text.toString(),
            "berat" to berat,
            "total" to total.toInt()
        )

        // SIMPAN KE HISTORY
        db.collection("history").add(history)

        // HAPUS TASK
        db.collection("tasks").document(taskId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Berhasil dikonfirmasi", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}
