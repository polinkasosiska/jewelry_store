package com.sysoliatina.jewelrystore.presentation.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.databinding.ActivityModeratorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModeratorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModeratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeratorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_moderator)
        navView.setupWithNavController(navController)
    }
}