package com.sysoliatina.jewelrystore.presentation.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.databinding.ActivityClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_client)
        navView.setupWithNavController(navController)
    }
}