package com.example.take_out.component

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.take_out.adapters.SharingItemRecyclerViewAdapter
import com.example.take_out.databinding.FragmentSharingBinding
import com.example.take_out.viewmodels.SharingModel


class SharingFragment : Fragment() {
    private var _binding: FragmentSharingBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 2
    private val sharingViewModel: SharingModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSharing.layoutManager = GridLayoutManager(context, columnCount)
        binding.rvSharing.adapter = SharingItemRecyclerViewAdapter(
                requireContext(),
                listOf()) { v, i ->
            onClickItem(v, i)
        }
        sharingViewModel.sharingList.observe(viewLifecycleOwner, Observer {
            Log.e("MAIN", "$it")
            with(binding.rvSharing.adapter as SharingItemRecyclerViewAdapter) {
                values = it
                notifyDataSetChanged()
            }
        })

        sharingViewModel.ready.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.layoutContent.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.VISIBLE
                binding.layoutContent.visibility = View.GONE
            }
        })

        binding.fbPublish.setOnClickListener {
            val intent = Intent(context, SharingPublishActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSharingBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun onClickItem(view: View, position: Int) {
        Toast.makeText(context, "🚀$position", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, SharingCommentActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
