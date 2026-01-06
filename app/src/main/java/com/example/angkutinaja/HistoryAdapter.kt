package com.example.angkutinaja.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.R
import com.example.angkutinaja.model.Task

class HistoryAdapter(
    private val list: List<Task>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNama: TextView = v.findViewById(R.id.tvNama)
        val tvJenis: TextView = v.findViewById(R.id.tvJenis)
        val tvTanggal: TextView = v.findViewById(R.id.tvTanggal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val t = list[position]
        holder.tvNama.text = "Nama: ${t.nama}"
        holder.tvJenis.text = "Jenis: ${t.jenisSampah}"
        holder.tvTanggal.text = "Tanggal: ${t.tanggal}"
    }
}
