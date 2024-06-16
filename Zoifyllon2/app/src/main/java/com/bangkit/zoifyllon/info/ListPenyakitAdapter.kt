package com.bangkit.zoifyllon.info

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.zoifyllon.R

class ListPenyakitAdapter (private val listCrew: List<Penyakit>) : RecyclerView.Adapter<ListPenyakitAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_detail_penyakit, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val context = holder.itemView.context
        val (name, description, photo, photobg) = listCrew[position]
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.imgPhoto.setImageResource(photo)
        holder.itemView.setOnClickListener {
            val intent= Intent(context, DetailPenyakit::class.java)
            intent.putExtra(DetailPenyakit.extraname, name)
            intent.putExtra(DetailPenyakit.extradesc, description)
            intent.putExtra(DetailPenyakit.extrapic, photo)
            intent.putExtra(DetailPenyakit.extrabg, photobg)
            context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int = listCrew.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Penyakit)
    }
}