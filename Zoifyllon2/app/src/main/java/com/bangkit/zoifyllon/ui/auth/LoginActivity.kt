package com.bangkit.zoifyllon.ui.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bangkit.zoifyllon.data.Result
import com.bangkit.zoifyllon.data.pref.UserModel
import com.bangkit.zoifyllon.databinding.ActivityLoginBinding
import com.bangkit.zoifyllon.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty()) {
                binding.emailEditText.error = "Email tidak boleh kosong"
            } else if (password.isEmpty()) {
                binding.loginPassword.error = "Password tidak boleh kosong"
            } else {
                lifecycleScope.launch {
                    viewModel.login(email, password).observe(this@LoginActivity) { result ->
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                showToast("Login berhasil!")
                                save(
                                    UserModel(
                                        id = result.data.data?.id.toString(),
                                        name = result.data.data?.name.toString(),
                                        email = result.data.data?.email.toString(),
                                        token = result.data.data?.token.toString(),
                                        isLogin = true
                                    )
                                )
                            }
                            is Result.Error -> {
                                showLoading(false)
                                showToast(result.error)
                            }
                        }
                    }
                }
            }
        }

        binding.signupRedirectText.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)

        }
    }

    private fun save(session: UserModel) {
        lifecycleScope.launch {
            viewModel.saveSession(session)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
