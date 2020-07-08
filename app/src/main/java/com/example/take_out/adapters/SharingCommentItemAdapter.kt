package com.example.take_out.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.take_out.R
import com.example.take_out.data.Comment
import com.example.take_out.data.Sharing
import com.example.take_out.data.User
import com.example.take_out.databinding.ItemSharingCommentBinding
import com.example.take_out.service.loadUrl


class SharingCommentItemAdapter(
        private val context: Context,
        private var _values: List<Comment>,
        private val callback: (View, Int) -> Unit
) : RecyclerView.Adapter<SharingCommentItemAdapter.ViewHolder>() {

    var values: List<Comment>
        get() = _values
        set(values) {
            _values = values
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                ItemSharingCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.tvUser.text = item.user.name
        holder.binding.tvContent.text = item.content
        holder.binding.imgUserFace.loadUrl(context, item.user.imageUUID, placeholder = R.drawable.sample_user_face)
        holder.itemView.setOnClickListener {
            callback(it, position)
        }
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(val binding: ItemSharingCommentBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        fun fakeItems(size: Int) = List(size) {
            Comment(content = "dsakjdlaksjdlsald",
                    user = User(name = "dasdasdjaks"),
                    sharing = Sharing(user = User(name = "daskjdask"))
            )
        }
    }
}