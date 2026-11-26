package com.example.belajarmobile

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_EMAIL = "extra_email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvNamaDiterima =
            findViewById<TextView>(R.id.tvNamaDiterima)

        val tvEmailDiterima =
            findViewById<TextView>(R.id.tvEmailDiterima)

        val btnKembali =
            findViewById<Button>(R.id.btnKembali)

        val nama = intent.getStringExtra(EXTRA_NAMA)
        val email = intent.getStringExtra(EXTRA_EMAIL)

        tvNamaDiterima.text = nama ?: "(nama tidak terkirim)"
        tvEmailDiterima.text = email ?: "(email tidak terkirim)"

        btnKembali.setOnClickListener {
            finish()
        }
    }
}