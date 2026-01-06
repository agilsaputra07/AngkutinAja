package com.example.angkutinaja.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angkutinaja.R
import com.example.angkutinaja.model.Task

class ScaleAdapter(
    private val list: List<Task>,
    private val onClick: (Task) -> Unit
) : RecyclerView.Adapter<ScaleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvJenis: TextView = view.findViewById(R.id.tvJenis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scale, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = list[position]

        holder.tvNama.text = task.nama
        holder.tvJenis.text = task.jenisSampah

        holder.itemView.setOnClickListener {
            onClick(task)
        }
    }
}
