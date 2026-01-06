package com.example.angkutinaja

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot

class MessageAdapter(
    private val context: Context,
    private val messageList: List<DocumentSnapshot>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvWa: TextView = view.findViewById(R.id.tvWa)
        val btnPesan: Button = view.findViewById(R.id.btnPesan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val doc = messageList[position]

        val nama = doc.getString("nama") ?: "-"
        val wa = doc.getString("wa") ?: ""

        holder.tvNama.text = nama
        holder.tvWa.text = wa

        holder.btnPesan.setOnClickListener {
            if (wa.isNotEmpty()) {
                val pesan =
                    "Permisi, kami dari petugas AngkutinAja sedang menuju ke lokasi anda, " +
                            "diharapkan anda berada di lokasi yang ditentukan. Terima kasih."

                val url = "https://wa.me/62${wa.drop(1)}?text=${Uri.encode(pesan)}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
        }
    }
}
