package com.example.take_out.module.sharing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.take_out.R

class SharingHostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sharing_host, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =SharingHostFragment()
    }
}