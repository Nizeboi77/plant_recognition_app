package com.example.projectprotel.ui.info

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectprotel.databinding.InfoBinding


class InfoActivity : AppCompatActivity(){
    private lateinit var binding: InfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = InfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Picture =getIntent().getStringExtra("picture")
        val Apa = BitmapFactory.decodeFile(Picture)
        binding.gambarinfo.setImageBitmap(Apa)


    }
}