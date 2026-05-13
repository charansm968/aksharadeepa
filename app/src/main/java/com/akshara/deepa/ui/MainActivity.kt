package com.akshara.deepa.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.akshara.deepa.R
import com.akshara.deepa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        b.bottomNav.setupWithNavController(navHost.navController)
    }
}
