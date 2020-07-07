package com.example.take_out.commponent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.take_out.R
import com.example.take_out.adapters.SharingItemRecyclerViewAdapter
import com.example.take_out.databinding.FragmentSharingBinding


class SharingFragment : Fragment() {
    private lateinit var binding: FragmentSharingBinding
    private var columnCount = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSharingBinding.inflate(inflater, container, false)
        binding.rvSharing.adapter = SharingItemRecyclerViewAdapter(MyItem.ITEMS) { view, i ->
            onClickItem(view, i)
        }
        binding.rvSharing.layoutManager = GridLayoutManager(context, columnCount)
        binding.fbPublish.setOnClickListener {
            val intent = Intent(context, SharingPublishActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    fun onClickItem(view: View, position: Int) {
        Toast.makeText(context, "🚀$position", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, SharingCommentActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) = SharingFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
            }
        }
    }

}


data class MyItem(val text: String, val drawableID: Int) {
    companion object {
        val ITEMS = MutableList(16) {

            MyItem("user_name", R.drawable.sample_pic)
        }
    }

}
