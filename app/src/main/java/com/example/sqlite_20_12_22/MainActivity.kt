package com.example.sqlite_20_12_22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlite_20_12_22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}