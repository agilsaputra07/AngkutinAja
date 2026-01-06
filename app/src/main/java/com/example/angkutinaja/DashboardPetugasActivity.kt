package com.example.angkutinaja

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.angkutinaja.databinding.ActivityDashboardPetugasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DashboardPetugasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardPetugasBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardPetugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        tampilkanNamaPetugas()

        // LOGOUT
        binding.btnLogoutPetugas.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // MAPS (DULUNYA SCHEDULE)
        binding.schedule.setOnClickListener {
            bukaGoogleMaps()
        }

        // TASK
        binding.task.setOnClickListener {
            startActivity(Intent(this, TaskPetugasActivity::class.java))
        }

        // SCALE
        binding.scale.setOnClickListener {
            startActivity(Intent(this, ScaleActivity::class.java))
        }

        // MESSAGE
        binding.message.setOnClickListener {
            startActivity(Intent(this, MessagePetugasActivity::class.java))
        }
    }

    private fun tampilkanNamaPetugas() {
        val user: FirebaseUser? = auth.currentUser

        if (user != null) {
            val nama = when {
                !user.displayName.isNullOrEmpty() -> user.displayName
                !user.email.isNullOrEmpty() -> user.email!!.substringBefore("@")
                else -> "Petugas"
            }

            binding.textView2.text = "Selamat datang, $nama"
        }
    }

    private fun bukaGoogleMaps() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps")
                )
            )
        }
    }
}
