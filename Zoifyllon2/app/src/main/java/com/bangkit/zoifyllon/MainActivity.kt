package com.bangkit.zoifyllon

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bangkit.zoifyllon.R
import com.bangkit.zoifyllon.databinding.ActivityMainBinding
import com.bangkit.zoifyllon.info.PenyakitActivity
import com.bangkit.zoifyllon.ui.OnBoardingActivity
import com.bangkit.zoifyllon.ui.auth.MainViewModel
import com.bangkit.zoifyllon.ui.auth.ViewModelFactory
import com.bangkit.zoifyllon.ui.classification.ResultActivity
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var textView: TextView
    private lateinit var cvScanner: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textView = findViewById(R.id.tv_user)
        cvScanner = findViewById(R.id.cv_scanner)

        // Check if the user has already logged in
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            } else {
                textView.text = user.email
            }

            binding.logoutButton.setOnClickListener {
                mainViewModel.logout()
            }

            cvScanner.setOnClickListener {
                val intent = Intent(applicationContext, PenyakitActivity::class.java)
                startActivity(intent)
            }

            binding.captureImage.setOnClickListener {
                val resultIntent = Intent(this@MainActivity, ResultActivity::class.java)
                startActivity(resultIntent)
            }
        }
    }
}