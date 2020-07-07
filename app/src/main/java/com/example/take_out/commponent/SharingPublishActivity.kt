package com.example.take_out.commponent

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
import com.example.take_out.R
import com.example.take_out.databinding.ActivitySharingPublishBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class SharingPublishActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
        const val REQUEST_PERMISSION = 3
    }

    private lateinit var bitmap: Bitmap
    private lateinit var binding: ActivitySharingPublishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharingPublishBinding.inflate(layoutInflater)
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnPublish.setOnClickListener {
            /* val sharing = Sharing().run {
                 userId = "一个用户"
                 title = "一个标题"
                 imagePath = "图片路径"
                 content = "这是内容"
                 val gson = Gson()
                 val json = gson.toJson(this)
                 json
             }*/
            Snackbar.make(it, "正在进行中🚀: sharing", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                    .show()
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