package com.example.take_out.component

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.take_out.R
import com.example.take_out.TakeOutApplication
import com.example.take_out.adapters.SharingItemAdapter
import com.example.take_out.data.Sharing
import com.example.take_out.databinding.FragmentSharingBinding
import com.example.take_out.viewmodels.SharingModel
import com.google.android.material.snackbar.Snackbar

const val TEST_IN_PHYSIC = false

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
        binding.rvSharing.adapter = SharingItemAdapter(
                requireContext(),
                listOf()) { sharing ->
            onClickItem(sharing)
        }
        binding.rvSharing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val topRowVerticalPosition =
                        if (recyclerView.childCount == 0) {
                            0
                        } else {
                            recyclerView.getChildAt(0).top
                        }
                binding.refreshLayout.isEnabled = topRowVerticalPosition >= 0 && !recyclerView.canScrollVertically(-1)

            }
        })

        // use for physical mobile test
        if (TEST_IN_PHYSIC) {
            binding.layout.visibility = View.VISIBLE
            (binding.rvSharing.adapter as SharingItemAdapter).values = SharingItemAdapter.fakeItems(16)
        } else {
            sharingViewModel.sharingList.observe(viewLifecycleOwner, Observer {
                with(binding.rvSharing.adapter as SharingItemAdapter) {
                    values = it
                }
            })
            sharingViewModel.loading.observe(viewLifecycleOwner, Observer {
                binding.refreshLayout.isRefreshing = it
            })
        }
        binding.refreshLayout.isRefreshing = true
        binding.refreshLayout.setOnRefreshListener {
            sharingViewModel.refreshData()
        }
        binding.fbPublish.setOnClickListener {
            val user = (requireActivity().application as TakeOutApplication).user
            if (user.id == -1) {
                Snackbar.make(view, getString(R.string.require_login_msg), Snackbar.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, SharingPublishActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSharingBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun onClickItem(sharing: Sharing) {
        val intent = Intent(context, SharingCommentActivity::class.java)
                .putExtra("sharing", sharing)
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
