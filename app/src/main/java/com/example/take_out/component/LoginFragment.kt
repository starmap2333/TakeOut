package com.example.take_out.component

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.take_out.TakeOutApplication
import com.example.take_out.data.User
import com.example.take_out.databinding.FragmentLoginBinding
import com.example.take_out.service.ResultCode
import com.example.take_out.service.Service
import com.example.take_out.service.UserService
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnConfirm.setOnClickListener {
                login()
            }
            btnRegister.setOnClickListener {
                val intent = Intent(context, EnrollActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun disableInput() {
        binding.run {
            btnConfirm.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            etUsername.isEnabled = false
            etPassword.isEnabled = false
            btnRegister.isEnabled = false
        }
    }

    private fun enableInput() {
        binding.run {
            etUsername.isEnabled = true
            etPassword.isEnabled = true
            btnRegister.isEnabled = true
            btnConfirm.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }


    private fun login() {
        binding.run {
            disableInput()
            if (checkInput()) {
                doLogin(etUsername.text.trim().toString(),
                        etPassword.text.trim().toString())
            } else {
                Snackbar.make(root, "phone and password required!", Snackbar.LENGTH_SHORT).show()
                enableInput()
            }
        }
    }

    private fun onLoginFailed() {
        binding.run {
            enableInput()
            Snackbar.make(root, "login failed!", Snackbar.LENGTH_SHORT).setAction("retry") {
                login()
            }.show()
        }
    }

    private fun onLoginSuccess(user: User?) {
        user?.let {
            if (user.id == -1) {
                return onLoginFailed()
            }
            (requireActivity().application as TakeOutApplication).userLiveData.value = user
        }
    }

    private fun doLogin(userName: String, password: String) {
        val param = UserService.UserLoginParam(phone = userName, password = password)
        Service.userService.login(param).observe(viewLifecycleOwner, Observer {
            when (it.code) {
                ResultCode.FAIL.code -> onLoginFailed()
                ResultCode.SUCCESS.code -> onLoginSuccess(it.data)
            }
        })
    }

    private fun checkInput(): Boolean {
        return binding.etUsername.text.isNotBlank() and
                binding.etPassword.text.isNotBlank()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

}