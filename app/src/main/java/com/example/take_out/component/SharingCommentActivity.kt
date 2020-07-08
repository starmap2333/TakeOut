package com.example.take_out.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.take_out.R
import com.example.take_out.TakeOutApplication
import com.example.take_out.adapters.SharingCommentItemAdapter
import com.example.take_out.data.Sharing
import com.example.take_out.databinding.ActivitySharingComentBinding
import com.example.take_out.databinding.FragmentCommentBoxBinding
import com.example.take_out.service.ResultCode
import com.example.take_out.service.loadUrl
import com.example.take_out.viewmodels.CommentModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class SharingCommentActivity : AppCompatActivity() {
    lateinit var binding: ActivitySharingComentBinding
    lateinit var commentModel: CommentModel

    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharingComentBinding.inflate(layoutInflater)
        binding.rvComment.adapter = SharingCommentItemAdapter(this,
                listOf()) { _, _ ->
        }

        setContentView(binding.root)
        setupData()
        setupView()
    }

    private fun setupData() {
        val sharing = intent.getParcelableExtra<Sharing>("sharing")!!
        commentModel = ViewModelProvider(this,
                CommentModel.ViewModelFactory(sharing)).get(CommentModel::class.java)
    }

    private fun setupView() {
        bottomSheetDialog = BottomSheetDialog(this@SharingCommentActivity,
                R.style.BottomSheetEdit).apply {
            val binding = FragmentCommentBoxBinding.inflate(layoutInflater)
            binding.btnSend.setOnClickListener {
                publishComment(binding.editComment.text.toString())
            }
            setContentView(binding.root)
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.toolbarLayout.title = commentModel.sharing.title
        binding.tvContent.text = commentModel.sharing.content
        binding.tvTopUser.text = commentModel.sharing.user.name
        binding.detailImage.loadUrl(this, commentModel.sharing.imageUUID)
        binding.imgTopUser.loadUrl(this, commentModel.sharing.user.imageUUID)

        binding.editComment.setOnClickListener { v ->
            //TODO 对输入进行检查
            (application as TakeOutApplication).let {
                if (it.user.id != -1) {
                    bottomSheetDialog.show()
                } else {
                    Snackbar.make(v, getString(R.string.require_login_msg), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        commentModel.commentList.observe(this, Observer {
            (binding.rvComment.adapter as SharingCommentItemAdapter).values = it
        })
    }


    private fun onPublishSuccess() {
        Snackbar.make(binding.root, getString(R.string.finish), Snackbar.LENGTH_SHORT).show()
        if (bottomSheetDialog.isShowing)
            bottomSheetDialog.dismiss()
        commentModel.refreshData()
    }

    private fun onPublishFailed() {
        Snackbar.make(binding.root, getString(R.string.finish), Snackbar.LENGTH_SHORT)
                .show()
    }

    private fun publishComment(content: String) {
        if (content.isBlank()) {
            Snackbar.make(binding.root, getString(R.string.require_content_msg), Snackbar.LENGTH_SHORT).show()
        } else {
            commentModel.publish(content, (application as TakeOutApplication).user.id).observe(this, Observer {
                when (it.code) {
                    ResultCode.SUCCESS.code -> onPublishSuccess()
                    ResultCode.FAIL.code -> onPublishFailed()
                }
            })
        }
    }
}