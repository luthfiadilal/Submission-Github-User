package com.example.ggram.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggram.R
import com.example.ggram.data.response.ItemsItem
import com.example.ggram.databinding.FragmentHomeBinding
import com.example.ggram.model.ViewModelFactory
import com.example.ggram.pref.SettingPreferences
import com.example.ggram.pref.SettingViewModel
import com.example.ggram.pref.dataStore
import com.example.ggram.ui.DetailActivity
import com.example.ggram.ui.ListUserAdapter
import kotlinx.coroutines.launch



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: SettingViewModel

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val recyclerView = binding.rvUsers

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        recyclerView.addItemDecoration(itemDecoration)

        val progressBar = binding.progressBar


        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading){
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) { users ->
            setUsersData(users)
        }

        viewModel.findUser()

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        binding.toolbar.setOnMenuItemClickListener { menuItem ->

            when (menuItem.itemId) {
                R.id.action_change_theme -> {
                    // Toggle tema (mode gelap <--> mode terang)
                    toggleNightMode()
                    true
                }
                else -> false
            }

        }

        mainViewModel.getThemeSetting().observe(viewLifecycleOwner) { isNightMode ->
            // Set tema berdasarkan pengaturan yang diperbarui
            val newNightMode = if (isNightMode) {
                AppCompatDelegate.MODE_NIGHT_YES // Aktifkan mode gelap
            } else {
                AppCompatDelegate.MODE_NIGHT_NO // Matikan mode gelap
            }

            AppCompatDelegate.setDefaultNightMode(newNightMode)

            // Set ikon menu berdasarkan tema yang aktif
            val iconId = if (isNightMode) {
                R.drawable.ph_sun
            } else {
                R.drawable.ph_moon
            }
            binding.toolbar.menu.findItem(R.id.action_change_theme)?.icon =
                ContextCompat.getDrawable(requireContext(), iconId)

        }

        return binding.root

    }


    private fun toggleNightMode() {
        val isNightMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES


        lifecycleScope.launch {
            mainViewModel.saveThemeSetting(!isNightMode)
        }

        // Set tema berdasarkan preferensi yang diperbarui
        val newNightMode = if (!isNightMode) {
            AppCompatDelegate.MODE_NIGHT_YES // Aktifkan mode gelap
        } else {
            AppCompatDelegate.MODE_NIGHT_NO // Matikan mode gelap
        }

        AppCompatDelegate.setDefaultNightMode(newNightMode)

        val iconResId = if (newNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.drawable.ph_moon
        } else {
            R.drawable.ph_sun
        }

        // Set ikon menu
        binding.toolbar.menu.findItem(R.id.action_change_theme)?.icon =
            ContextCompat.getDrawable(requireContext(), iconResId)


    }

    private fun setUsersData(users: List<ItemsItem>) {

        adapter = ListUserAdapter { userItem ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("username", userItem.login) // Kirim data pengguna yang diklik
            startActivity(intent)
        }
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
        Log.d(TAG, "user : $users")

    }

}