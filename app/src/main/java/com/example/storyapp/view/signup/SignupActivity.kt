package com.example.storyapp.view.signup

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivitySignupBinding
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: SignupViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        signup()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivSignup, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun signup() {
        binding.apply {
            val edtRegisterName = binding.edtName.text
            val edtRegisterEmail = binding.edtEmail.text
            val edtRegisterPassword = binding.edtPassword.text

            btnSignup.setOnClickListener {
                if (edtRegisterName!!.isEmpty() || edtRegisterEmail!!.isEmpty() || edtRegisterPassword!!.isEmpty()) {
                    showToast(R.string.empty_form)
                } else if (!isValidEmail(edtRegisterEmail) || edtRegisterPassword.length < 8) {
                    showToast(R.string.invalid_form)
                } else {
                    showLoading(true)
                    uploadRegister()
                    showToast(R.string.register_success)
                    moveToLogin()
                }
            }
        }
    }

    private fun uploadRegister() {
        binding.apply {
            viewModel.registerUser(
                edtName.text.toString(),
                edtEmail.text.toString(),
                edtPassword.text.toString()
            )
        }
    }

    private fun moveToLogin() {
        viewModel.register.observe(this) { response ->
            if (!response.error!!) {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: Int) {
        Toast.makeText(this@SignupActivity, message, Toast.LENGTH_LONG).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}