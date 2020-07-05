package com.example.take_out.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.take_out.databinding.SharingItemBinding
import com.example.take_out.module.sharing.MyItem

class SharingItemRecyclerViewAdapter(
        private val values: List<MyItem>
) : RecyclerView.Adapter<SharingItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                SharingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.tvUser.text = item.text
        holder.binding.imgTitle.setImageResource(item.drawableID)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: SharingItemBinding) : RecyclerView.ViewHolder(binding.root)
}