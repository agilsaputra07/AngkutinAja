package com.example.angkutinaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.angkutinaja.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // 🔹 Tombol Register
        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmailReg.text.toString().trim()
            val password = binding.etPasswordReg.text.toString().trim()
            val role = if (binding.rbUser.isChecked) "user" else "petugas"

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(nama, email, password, role)
        }

        // 🔹 Tombol Kembali ke Login
        binding.btnBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun registerUser(
        nama: String,
        email: String,
        password: String,
        role: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener

                val userData = hashMapOf(
                    "uid" to uid,
                    "nama" to nama,
                    "email" to email,
                    "role" to role
                )

                db.collection("users").document(uid)
                    .set(userData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Registrasi berhasil, silakan login",
                            Toast.LENGTH_SHORT
                        ).show()

                        // 🔥 LOGOUT BIAR TIDAK AUTO LOGIN
                        auth.signOut()

                        // 🔥 KEMBALI KE LOGIN & BERSIHKAN STACK
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Gagal menyimpan data user!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Registrasi gagal: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
