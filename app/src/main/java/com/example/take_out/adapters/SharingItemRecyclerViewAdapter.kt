package com.example.take_out.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.take_out.R
import com.example.take_out.data.Sharing
import com.example.take_out.data.User
import com.example.take_out.databinding.ItemSharingBinding
import com.example.take_out.service.loadUrl

class SharingItemRecyclerViewAdapter(
        private val context: Context,
        var values: List<Sharing>,
        private val callback: (View, Int) -> Unit
) : RecyclerView.Adapter<SharingItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                ItemSharingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.tvUser.text = item.user.name
        holder.binding.tvTitle.text = item.title
        holder.binding.imgTitle.loadUrl(context, item.imageUUID)
        holder.binding.imgUserFace.loadUrl(context, item.user.imageUUID, placeholder = R.drawable.sample_user_face)
        holder.itemView.setOnClickListener {
            callback(it, position)
        }
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(val binding: ItemSharingBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        fun fakeItems(size: Int) = List(size) {
            Sharing(user = User())
        }
    }
}