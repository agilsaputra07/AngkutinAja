package com.example.angkutinaja

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ScaleDetailActivity : AppCompatActivity() {

    private val hargaPerKg = 3000
    private val db = FirebaseFirestore.getInstance()
    private var totalHarga = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_detail)

        val tvNama = findViewById<TextView>(R.id.tvNama)
        val etBerat = findViewById<EditText>(R.id.etBerat)
        val tvHarga = findViewById<TextView>(R.id.tvHarga)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val btnKonfirmasi = findViewById<Button>(R.id.btnKonfirmasi)

        val taskId = intent.getStringExtra("TASK_ID") ?: return
        val nama = intent.getStringExtra("NAMA") ?: ""
        val jenis = intent.getStringExtra("JENIS") ?: ""

        tvNama.text = nama

        // 🔢 HITUNG
        btnHitung.setOnClickListener {
            val beratText = etBerat.text.toString()

            if (beratText.isEmpty()) {
                Toast.makeText(this, "Masukkan berat terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val berat = beratText.toDouble()
            totalHarga = berat * hargaPerKg
            tvHarga.text = "Total: Rp ${totalHarga.toInt()}"
        }

        // ✅ KONFIRMASI
        btnKonfirmasi.setOnClickListener {
            if (totalHarga == 0.0) {
                Toast.makeText(this, "Hitung harga terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val berat = etBerat.text.toString().toDouble()

            // Update task → selesai
            db.collection("tasks")
                .document(taskId)
                .update("status", "Selesai")

            // Simpan ke history
            val history = hashMapOf(
                "nama" to nama,
                "jenisSampah" to jenis,
                "berat" to berat,
                "harga" to totalHarga
            )

            db.collection("history")
                .add(history)
                .addOnSuccessListener {
                    Toast.makeText(this, "Berhasil dikonfirmasi", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}

