package com.bangkit.zoifyllon.info

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.zoifyllon.R

class PenyakitActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var penyakitAdapter: ListPenyakitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_penyakit)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        showRecyclerList()

        val backButton = findViewById<ImageView>(R.id.backImageView)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getListPenyakit(): ArrayList<Penyakit> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataPhotobg = resources.obtainTypedArray(R.array.data_photobg)
        val listPenyakit = ArrayList<Penyakit>()

        val size = minOf(
            dataDescription.size,
            dataName.size,
            dataPhoto.length(),
            dataPhotobg.length()
        )

        for (i in 0 until size) {
            val penyakit = Penyakit(
                dataName[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1),
                dataPhotobg.getResourceId(i, -1)
            )
            listPenyakit.add(penyakit)
        }

        dataPhoto.recycle()
        dataPhotobg.recycle()

        return listPenyakit
    }

    private fun showRecyclerList() {
        penyakitAdapter = ListPenyakitAdapter(getListPenyakit())
        recyclerView.adapter = penyakitAdapter

        penyakitAdapter.setOnItemClickCallback(object : ListPenyakitAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Penyakit) {
                showSelectedPenyakit(data)
            }
        })
    }

    private fun showSelectedPenyakit(penyakit: Penyakit) {
        val detailIntent = Intent(this@PenyakitActivity, DetailPenyakit::class.java).apply {
            putExtra(DetailPenyakit.extraname, penyakit.name)
            putExtra(DetailPenyakit.extradesc, penyakit.description)
            putExtra(DetailPenyakit.extrapic, penyakit.photo)
            putExtra(DetailPenyakit.extrabg, penyakit.photobg)
        }
        startActivity(detailIntent)
    }
}