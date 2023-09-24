package com.example.ggram.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.ggram.R
import com.example.ggram.database.FavoriteUser
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.ggram.databinding.ActivityDetailBinding
import com.example.ggram.ui.favorite.FavoriteViewModel
import com.google.android.material.appbar.MaterialToolbar

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    @SuppressLint("ObjectAnimatorBinding", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val username = intent.getStringExtra("username") // Ambil username dari intent
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }



        val fabFavorite = binding.fabFavorite
        viewModel.userDetailLiveData.observe(this) { userDetail ->
            // Update UI dengan data pengguna detail
            if (userDetail != null) {
                binding.detailNameUser.text = userDetail.login
                binding.sbDetailNameUser.text = userDetail.login
                binding.tvFollowers.text = userDetail.followers.toString()
                binding.tvFollowing.text = userDetail.following.toString()
                Glide.with(binding.root).load(userDetail.avatarUrl).into(binding.deatilImgUser)

                progressBar.visibility = View.GONE

                val favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

                // Inisialisasi status favorit berdasarkan hasil observasi
                var isFavorite = false

                favoriteViewModel.getFavoriteUserByUsername(userDetail.login).observe(this) { favoriteUser ->
                    if (favoriteUser != null && favoriteUser.isFavorite) {
                        isFavorite = true
                        // Jika pengguna ada di daftar favorit, ubah ikon FAB menjadi hati yang diisi
                        fabFavorite.setImageResource(R.drawable.ic_heart_fill)
                    } else {
                        isFavorite = false
                        // Jika pengguna tidak ada di daftar favorit, ubah ikon FAB menjadi hati yang tidak diisi
                        fabFavorite.setImageResource(R.drawable.ic_heart_line)
                    }
                }

                fabFavorite.setOnClickListener {
                    val userToAdd = FavoriteUser(
                        username = userDetail.login,
                        avatarUrl = userDetail.avatarUrl,
                        isFavorite = !isFavorite
                    )
                    val userToDelete = FavoriteUser(
                        username = userDetail.login,
                        avatarUrl = userDetail.avatarUrl,
                        isFavorite = isFavorite
                    )

                    if (isFavorite) {
                        // Jika pengguna ada di daftar favorit, hapus pengguna dari daftar favorit
                        favoriteViewModel.deleteUser(userToDelete)
                        Toast.makeText(this, "Pengguna dihapus dari favorit", Toast.LENGTH_SHORT).show()
                    } else {
                        // Jika pengguna tidak ada di daftar favorit, tambahkan pengguna ke daftar favorit
                        favoriteViewModel.insertUser(userToAdd)
                        Toast.makeText(this, "Pengguna ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
                    }

                    // Tampilkan pesan atau lakukan tindakan lain jika diperlukan

                    // Setelah mengubah status favorit, perbarui isFavorite
                    isFavorite = !isFavorite
                }

            } else {
                progressBar.visibility = View.VISIBLE
            }

        }

        binding.btnShare.setOnClickListener {
            shareUserDetails()
        }



        if (username != null) {
            viewModel.fetchUserDetail(username)
            viewModel.findFollowers(username)
            viewModel.findFollowing(username)

        }

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        val sectionPagerAdapter = SectionPagerAdapter(this)

        tabLayout.tabRippleColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tab_selector_color))


        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            } R.id.action_share -> {
                shareUserDetails()
                true
            } else -> false
        }

    }

    private fun shareUserDetails() {
        val userDetail = viewModel.userDetailLiveData.value

        if (userDetail != null) {
            val username = userDetail.login
            val followers = userDetail.followers
            val following = userDetail.following

            // Membuat pesan teks yang akan dibagikan
            val shareText = "Detail Pengguna:\n" +
                    "Nama: ${userDetail.name}\n" +
                    "Username: $username\n" +
                    "Followers: $followers\n" +
                    "Following: $following"

            // Membuat Intent untuk melakukan proses berbagi
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)

            // Memulai proses berbagi
            startActivity(Intent.createChooser(shareIntent, "Bagikan Detail Pengguna"))
        } else {
            // Handle jika data pengguna tidak tersedia
            Toast.makeText(this, "Data pengguna tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }

}