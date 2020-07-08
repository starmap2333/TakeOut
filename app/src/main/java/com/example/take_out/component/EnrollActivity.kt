package com.example.take_out.component

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.take_out.data.User
import com.example.take_out.databinding.ActivityEnrollBinding
import com.example.take_out.service.ResultCode
import com.example.take_out.service.Service
import com.google.android.material.snackbar.Snackbar
import java.util.*

class EnrollActivity : AppCompatActivity() {
    lateinit var binding: ActivityEnrollBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
    }

    private fun disableInput() {
        binding.run {
            btnRegister.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            etUsername.isEnabled = false
            etPwd.isEnabled = false
            etSurepwd.isEnabled = false
            btnRegister.isEnabled = false
        }
    }

    private fun enableInput() {
        binding.run {
            etUsername.isEnabled = true
            etPwd.isEnabled = true
            etSurepwd.isEnabled = true
            btnRegister.isEnabled = true
            btnRegister.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    private fun checkInput(): Boolean {
        return binding.etUsername.text.isNotBlank() and
                binding.etPwd.text.isNotBlank() and
                binding.etSurepwd.text.isNotBlank()
    }

    private fun checkPassword(): Boolean {
        return binding.etPwd.text.trim() == binding.etSurepwd.text.trim()
    }

    private fun enroll() {
        binding.run {
            disableInput()
            if (checkInput()) {
                if (checkPassword()) {
                    doEnroll(etUsername.text.trim().toString(),
                            etPwd.text.trim().toString())
                } else {
                    Snackbar.make(root, "password not the same!", Snackbar.LENGTH_SHORT).show()
                    enableInput()
                }
            } else {
                Snackbar.make(root, "all field required!", Snackbar.LENGTH_SHORT).show()
                enableInput()
            }
        }
    }

    private fun doEnroll(phone: String, password: String) {
        val username = UUID.randomUUID().toString().substring(0..7)
        val user = User(phone = phone, password = password, name = username)
        Service.userService.enroll(user).observe(this, Observer {
            when (it.code) {
                ResultCode.FAIL.code -> onEnrollFailed()
                ResultCode.SUCCESS.code -> onEnrollSuccess(it.data)
            }
        })
    }

    private fun onEnrollSuccess(data: Int?) {
        if (data != -1) {
            finish()
        } else {
            onEnrollFailed()
        }
    }

    private fun onEnrollFailed() {
        binding.run {
            enableInput()
            Snackbar.make(root, "login failed!", Snackbar.LENGTH_SHORT).setAction("retry") {
                enroll()
            }.show()
        }
    }

    private fun setUpView() {
        binding.run {
            btnBack.setOnClickListener {
                finish()
            }
            btnRegister.setOnClickListener {
                enroll()
            }
        }
    }
}