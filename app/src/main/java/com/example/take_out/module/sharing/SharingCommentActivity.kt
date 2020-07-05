package com.example.take_out.module.sharing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.take_out.R
import com.example.take_out.databinding.ActivitySharingComentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class SharingCommentActivity : AppCompatActivity() {
    lateinit var binding: ActivitySharingComentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharingComentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        binding.editComment.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(
                    layoutInflater.inflate(R.layout.fragment_comment_box, null)
            )
            bottomSheetDialog.show()
        }
    }
}