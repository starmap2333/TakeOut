package com.example.take_out.component

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.take_out.R
import com.example.take_out.TakeOutApplication
import com.example.take_out.data.Sharing
import com.example.take_out.databinding.ActivitySharingPublishBinding
import com.example.take_out.service.ResultCode
import com.example.take_out.service.Service
import com.example.take_out.service.toMultiPartBodyPart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream


class SharingPublishActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
        const val REQUEST_PERMISSION = 3
    }

    private var bitmap: Bitmap? = null
    private lateinit var binding: ActivitySharingPublishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharingPublishBinding.inflate(layoutInflater)
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnPublish.setOnClickListener {
            it.isEnabled = false
            publish()
        }

        binding.imgPublish.setOnClickListener {
            MaterialAlertDialogBuilder(this).setItems(
                    arrayOf(getString(R.string.use_camera), getString(R.string.use_pick))
            ) { _, i ->
                when (i) {
                    0 -> takePic()
                    1 -> pickPic()
                }
            }.show()
        }
        setContentView(binding.root)
    }

    private fun publish() {
        //TODO 用户输入检查
        if (bitmap != null) {
            binding.btnPublish.isEnabled = false
            val title = binding.edtTitle.text.toString().trim()
            val content = binding.edtContent.text.toString().trim()
            val userId = (application as TakeOutApplication).user.id
            val out = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)
            out.close()
            Service.sharingService.publishSharing(out.toByteArray().toMultiPartBodyPart(),
                    title, content, userId).observe(this, Observer { result ->
                when (result.code) {
                    ResultCode.SUCCESS.code -> onPublishSuccess(result.data)
                    ResultCode.FAIL.code -> onPublishFailed()
                }
            })
        }
    }

    private fun onPublishFailed() {
        Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
                .setAction(R.string.retry) {
                    publish()
                }.show()
        binding.btnPublish.isEnabled = true
    }

    private fun onPublishSuccess(sharing: Sharing?) {
        if (sharing == null) return onPublishFailed()
        else {
            val intent = Intent(this, SharingCommentActivity::class.java)
                    .putExtra("sharing", sharing)
            startActivity(intent)
            finish()
        }
    }

    private fun takePic() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.CAMERA), REQUEST_PERMISSION)
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun pickPic() {
        Intent(Intent.ACTION_PICK).also { takePictureIntent ->
            takePictureIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_PICK)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION -> if (grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                takePic()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.extras?.get("data")?.let {
                        bitmap = it as Bitmap
                        binding.imgPublish.setImageBitmap(bitmap)
                    }
                }
            }
            REQUEST_IMAGE_PICK -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let {
                        bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it))
                        binding.imgPublish.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

}