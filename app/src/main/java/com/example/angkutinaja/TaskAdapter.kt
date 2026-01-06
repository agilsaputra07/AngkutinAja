package com.example.angkutinaja.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.R
import com.example.angkutinaja.model.Task

class TaskAdapter(
    private val list: List<Task>
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val tvJenis: TextView = view.findViewById(R.id.tvJenis)
        val tvWa: TextView = view.findViewById(R.id.tvWa)
        val btnLokasi: Button = view.findViewById(R.id.btnLokasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = list[position]

        holder.tvNama.text = "Nama: ${task.nama}"
        holder.tvTanggal.text = "Tanggal: ${task.tanggal}"
        holder.tvJenis.text = "Jenis Sampah: ${task.jenisSampah}"
        holder.tvWa.text = "WA: ${task.wa}"

        holder.btnLokasi.setOnClickListener {
            val context = holder.itemView.context

            // 🔥 AMAN: Bisa teks alamat + link
            val uri = Uri.parse(
                "geo:0,0?q=" + Uri.encode(task.lokasi)
            )

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")

            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Google Maps tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
