package com.bangkit.zoifyllon.info

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.zoifyllon.R

class DetailPenyakit : AppCompatActivity() {
    companion object {
        const val extraname = "EXTRA_NAME"
        const val extradesc = "EXTRA_DESC"
        const val extrapic = "EXTRA_PIC"
        const val extrabg = "EXTRA_BG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_penyakit)

        val actionbar = supportActionBar
        actionbar?.title = "Detail"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra(extraname)
        val desc = intent.getStringExtra(extradesc)
        val pic = intent.getIntExtra(extrapic, 0)
        val picbg = intent.getIntExtra(extrabg, 0)

        if (name == null || desc == null || pic == 0 || picbg == 0) {
            // Handle missing extras
            finish() // Close activity if necessary extras are missing
            return
        }

        val nametext: TextView = findViewById(R.id.penyakit_name)
        val desctext: TextView = findViewById(R.id.penyakit_desc)
        val penyakitpic: ImageView = findViewById(R.id.penyakit_pic)
        val penyakitbg: ImageView = findViewById(R.id.penyakit_bg)

        nametext.text = name
        desctext.text = desc
        penyakitpic.setImageResource(pic)
        penyakitbg.setImageResource(picbg)

        val backButton = findViewById<ImageView>(R.id.backImageView)
        backButton.setOnClickListener {
            onBackPressed()

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}