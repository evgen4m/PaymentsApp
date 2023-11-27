package com.esoft.paymentsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esoft.paymentsapp.databinding.ActivityMainBinding

class FragmentContainerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}