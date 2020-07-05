package com.example.take_out.module.sharing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        binding.rvSharing.adapter = SharingItemRecyclerViewAdapter(MyItem.ITEMS)
        binding.rvSharing.layoutManager = GridLayoutManager(context, columnCount)
        binding.fbPublish.setOnClickListener {
            findNavController().navigate(R.id.action_sharingFragment_to_sharingPublishFragment)
        }
        return binding.root
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) = SharingPublishFragment().apply {
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
