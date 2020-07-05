package com.example.take_out.module.sharing

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.take_out.R
import com.example.take_out.databinding.ActivitySharingPublishBinding
import com.google.android.material.snackbar.Snackbar


class SharingPublishActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
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
            Snackbar.make(it, "正在进行中🚀", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                    .show()
        }

        binding.imgPublish.setOnClickListener {
            takePic()
        }
        setContentView(binding.root)
    }

    private fun takePic() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            bitmap = imageBitmap
            binding.imgPublish.setImageBitmap(imageBitmap)
        }
    }

}