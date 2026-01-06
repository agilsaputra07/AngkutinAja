package com.example.angkutinaja

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class InputWasteActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_waste)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val etNama = findViewById<EditText>(R.id.etNama)
        val etWa = findViewById<EditText>(R.id.etWa)
        val etTanggal = findViewById<EditText>(R.id.etTanggal)
        val etLokasi = findViewById<EditText>(R.id.etLokasi)
        val spinner = findViewById<Spinner>(R.id.spJenisSampah)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnOpenMaps = findViewById<Button>(R.id.btnOpenMaps)

        // 🔥 BUTTON GOOGLE MAPS
        btnOpenMaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.google.com/maps")
            startActivity(intent)
        }

        // Spinner
        val jenis = arrayOf("Organik", "Anorganik", "B3", "Campuran")
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, jenis)

        // Date Picker
        etTanggal.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    etTanggal.setText("$d/${m + 1}/$y")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // SUBMIT
        btnSubmit.setOnClickListener {

            val nama = etNama.text.toString().trim()
            val wa = etWa.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()
            val lokasi = etLokasi.text.toString().trim()
            val jenisSampah = spinner.selectedItem.toString()

            val userId = auth.currentUser?.uid

            if (userId == null) {
                Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nama.isEmpty() || wa.isEmpty() || tanggal.isEmpty() || lokasi.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = hashMapOf(
                "nama" to nama,
                "wa" to wa,
                "jenisSampah" to jenisSampah,
                "tanggal" to tanggal,
                "lokasi" to lokasi,
                "status" to "Menunggu",
                "userId" to userId,
                "createdAt" to Date()
            )

            db.collection("tasks")
                .add(task)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data berhasil dikirim", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal kirim data", Toast.LENGTH_LONG).show()
                }
        }
    }
}
