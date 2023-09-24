package com.example.ggram.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggram.R
import com.example.ggram.data.response.ItemsItem
import com.example.ggram.data.response.UserApiResponse
import com.example.ggram.data.response.UserApiResponseItem
import com.example.ggram.data.response.UserResponse
import com.example.ggram.data.retrofit.ApiConfig
import com.example.ggram.databinding.ActivityMainBinding
import com.example.ggram.model.ViewModelFactory
import com.example.ggram.pref.SettingPreferences
import com.example.ggram.pref.SettingViewModel
import com.example.ggram.pref.dataStore
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val pref = SettingPreferences.getInstance(this@MainActivity.dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    // Navigasi ke fragment Home
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.menu_search -> {
                    // Navigasi ke fragment Search
                    navController.navigate(R.id.navigation_search)
                    true
                }

                R.id.menu_favorite -> {
                    // Navigasi ke fragment Favorite
                    navController.navigate(R.id.navigation_favorite)
                    true
                }
                R.id.menu_about -> {
                    // Navigasi ke fragment About
                    navController.navigate(R.id.navigation_about)
                    true
                }
                else -> false
            }
        }

    }



}