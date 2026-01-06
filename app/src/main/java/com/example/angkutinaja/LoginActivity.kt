package com.example.angkutinaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.angkutinaja.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Tombol Login
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString().trim()
            val password = binding.etPasswordLogin.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi semua field!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        // Ke Register
        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = auth.currentUser?.uid
                if (uid == null) {
                    Toast.makeText(this, "UID tidak ditemukan!", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // Ambil data user dari Firestore
                db.collection("users").document(uid).get()
                    .addOnSuccessListener { doc ->

                        if (!doc.exists()) {
                            Toast.makeText(this, "Data user tidak ditemukan!", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        val role = doc.getString("role")

                        if (role == null) {
                            Toast.makeText(this, "Role belum diatur!", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        // Arahkan sesuai role
                        when (role.lowercase()) {
                            "petugas" -> {
                                startActivity(Intent(this, DashboardPetugasActivity::class.java))
                            }
                            "user", "warga" -> {
                                startActivity(Intent(this, DashboardWargaActivity::class.java))
                            }
                            else -> {
                                Toast.makeText(this, "Role tidak valid!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal ambil data user!", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login gagal: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
