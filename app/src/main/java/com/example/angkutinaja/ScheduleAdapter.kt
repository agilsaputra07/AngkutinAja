package com.example.angkutinaja.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.R
import com.example.angkutinaja.model.Task

class ScheduleAdapter(
    private val list: List<Task>
) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNama: TextView = v.findViewById(R.id.tvNama)
        val tvTanggal: TextView = v.findViewById(R.id.tvTanggal)
        val tvJenis: TextView = v.findViewById(R.id.tvJenis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val t = list[position]
        holder.tvNama.text = "Nama: ${t.nama}"
        holder.tvTanggal.text = "Tanggal: ${t.tanggal}"
        holder.tvJenis.text = "Jenis: ${t.jenisSampah}"
    }
}
