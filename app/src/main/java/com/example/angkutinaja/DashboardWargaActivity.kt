package com.example.angkutinaja

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class DashboardWargaActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_warga)

        auth = FirebaseAuth.getInstance()

        // Ambil view sesuai XML TERBARU
        val menuInputWaste = findViewById<LinearLayout>(R.id.menuInputWaste)
        val menuSchedule = findViewById<LinearLayout>(R.id.menuSchedule)
        val menuHistory = findViewById<LinearLayout>(R.id.menuHistory)
        val btnLogout = findViewById<MaterialButton>(R.id.btnLogoutWarga)

        // 🔹 Input Waste
        menuInputWaste.setOnClickListener {
            startActivity(Intent(this, InputWasteActivity::class.java))
        }

        // 🔹 Schedule
        menuSchedule.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

        // 🔹 History
        menuHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        // 🔹 Logout
        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
