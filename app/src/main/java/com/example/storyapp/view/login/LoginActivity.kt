package com.example.storyapp.view.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.data.pref.UserModel
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.view.ViewModelFactory
import com.example.storyapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        login()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun login() {
        val edtLoginEmail = binding.edtEmail.text
        val edtLoginPassword = binding.edtPassword.text

        binding.btnLogin.setOnClickListener {
            if (edtLoginEmail!!.isEmpty() || edtLoginPassword!!.isEmpty()) {
                showToast(R.string.empty_form)
            } else if (!isValidEmail(edtLoginEmail.toString()) || edtLoginPassword.length < 8) {
                showToast(R.string.invalid_form)
            } else {
                showLoading(true)
                uploadLogin()
                viewModel.userLogin()
                moveToMain()
            }
        }
    }

    private fun uploadLogin() {
        val edtRegisterEmail = binding.edtEmail.text.toString()
        val edtRegisterPassword = binding.edtPassword.text.toString()

        viewModel.loginUser(
            edtRegisterEmail,
            edtRegisterPassword
        )
        viewModel.login.observe(this) { response ->
            saveSession(
                UserModel(
                    response.loginResult?.name!!,
                    "Bearer " + (response.loginResult.token),
                    true
                )
            )
        }
    }

    private fun saveSession(session: UserModel) {
        viewModel.saveSession(session)
    }

    private fun moveToMain() {
        viewModel.login.observe(this) { response ->
            if (!response.error!!) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: Int) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}