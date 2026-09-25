package com.example.nawfal_3tif.Pertemuan3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nawfal_3tif.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.nawfal_3tif.databinding.ActivityThirdBinding
import android.content.Intent
class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//
//        val inputNoTujuan = findViewById<EditText>(R.id.editTextPhone)
//        val btnSubmit = findViewById<Button>(R.id.button2)

        binding.button2.setOnClickListener {
//            val inputNama = binding.editTextPhone.text
//            Toast.makeText(this, "Pesan berhasil dikirim ke $inputNama", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ThirdResultActivity::class.java)
            startActivity(intent)
        }
    }
}