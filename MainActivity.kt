package com.servermanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.servermanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_servers -> {
                    loadFragment(ServersFragment())
                    true
                }
                R.id.nav_terminal -> {
                    loadFragment(TerminalFragment())
                    true
                }
                R.id.nav_vpn -> {
                    loadFragment(VpnFragment())
                    true
                }
                R.id.nav_database -> {
                    loadFragment(DatabaseFragment())
                    true
                }
                else -> false
            }
        }

        // Load default fragment
        loadFragment(ServersFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
