package com.example.angkutinaja.model

data class WasteRequest(
    val jenisSampah: String = "",
    val berat: String = "",
    val tanggal: String = "",
    val status: String = "Menunggu",
    val timestamp: Long = System.currentTimeMillis()
)
