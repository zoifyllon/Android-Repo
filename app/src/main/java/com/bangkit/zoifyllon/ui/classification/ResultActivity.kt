package com.bangkit.zoifyllon.ui.classification

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bangkit.zoifyllon.R
import com.bangkit.zoifyllon.api.ApiService
import com.bangkit.zoifyllon.data.datamodel.DetectResponse
import com.bangkit.zoifyllon.databinding.ActivityResultBinding
import com.bangkit.zoifyllon.ui.classification.CameraActivity.Companion.CAMERAX_RESULT
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import com.bangkit.zoifyllon.ui.classification.utils.uriToFile

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var currentImageUri: Uri? = null
    private var authToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Back Button
        val backButton = findViewById<ImageView>(R.id.backImageView)
        backButton.setOnClickListener {
            onBackPressed()
        }

        authToken = intent.getStringExtra("TOKEN") // Ambil token dari intent

        startCameraX()
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.resultImage.setImageURI(it)
            classifyZoifyllon(it)
        }
    }

    private fun startCameraX() {
        val intent = Intent(this@ResultActivity, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_CANCELED) {
            finish()
        } else if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun classifyZoifyllon(uri: Uri) {
        val file = uriToFile(uri, this)
        val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("detectImage", file.name, requestFile)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.128.104.206/") // Ganti dengan URL base API Anda
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        // Debug: cetak token ke logcat
        Log.d("ResultActivity", "Token: $authToken")

        val call = service.detectDisease(body, "Bearer $authToken") // Ganti dengan token yang diperoleh dari login
        call.enqueue(object : Callback<DetectResponse> {
            override fun onResponse(call: Call<DetectResponse>, response: Response<DetectResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        displayResults(it)
                    }
                } else {
                    showError(response.errorBody()?.string() ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<DetectResponse>, t: Throwable) {
                showError(t.message ?: "Unknown error")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayResults(detectResponse: DetectResponse) {
        runOnUiThread {
            val resultText = StringBuilder()
            val additionalText = StringBuilder()

            detectResponse.data.diseases.forEach {
                resultText.append("Disease: ${it.disease},\nPercentage: ${it.percentage}%,\n_____________________________________\n\n")
                additionalText.append("Gejala: ${it.symptoms},\n\nPencegahan: ${it.prevents},\n____________________________________\n\n")
            }

            binding.resultText.text = resultText.toString()
            binding.resultTextAdditional.text = additionalText.toString()
        }
    }


    private fun showError(error: String) {
        runOnUiThread {
            Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
        }
    }
}
