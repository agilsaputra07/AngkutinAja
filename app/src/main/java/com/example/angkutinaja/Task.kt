package com.example.angkutinaja.model

import com.google.firebase.Timestamp

data class Task(
    var id: String = "",          // 🔥 WAJIB var
    val nama: String = "",
    val wa: String = "",
    val jenisSampah: String = "",
    val tanggal: String = "",
    val lokasi: String = "",
    val status: String = "",
    val userId: String = "",
    val createdAt: Timestamp? = null
)
