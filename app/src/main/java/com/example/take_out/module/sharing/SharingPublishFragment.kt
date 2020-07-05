package com.example.take_out.module.sharing

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.take_out.databinding.SharingPublishFragmentBinding


class SharingPublishFragment : Fragment() {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1

        @JvmStatic
        fun newInstance() = SharingPublishFragment()
    }

    lateinit var bitmap: Bitmap
    lateinit var binding: SharingPublishFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = SharingPublishFragmentBinding.inflate(layoutInflater, container, false)
        binding.btnBack.setOnClickListener {
            // 收起输入法键盘
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            findNavController().navigateUp()
        }
        binding.imgPublish.setOnClickListener {
            takePic()
        }
        return binding.root
    }

    private fun takePic() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            bitmap = imageBitmap
            binding.imgPublish.setImageBitmap(imageBitmap)
        }
    }

}