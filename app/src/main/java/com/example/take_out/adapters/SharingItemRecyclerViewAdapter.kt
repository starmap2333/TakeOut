package com.example.take_out.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.take_out.commponent.MyItem
import com.example.take_out.databinding.ItemSharingBinding

class SharingItemRecyclerViewAdapter(
        private val values: List<MyItem>,
        private val callback: (View, Int) -> Unit
) : RecyclerView.Adapter<SharingItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                ItemSharingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.tvUser.text = item.text
        holder.binding.imgTitle.setImageResource(item.drawableID)
        holder.itemView.setOnClickListener {
            callback(it, position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ItemSharingBinding) : RecyclerView.ViewHolder(binding.root)
}