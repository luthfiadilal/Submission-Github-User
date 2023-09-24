package com.example.ggram.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggram.R
import com.example.ggram.databinding.FragmentFavoriteBinding
import com.example.ggram.ui.DetailActivity
import com.example.ggram.ui.FavoritAdapter

class FavoriteFragment : Fragment() {
    private lateinit var favoritAdapter: FavoritAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: FragmentFavoriteBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        val recyclerView = binding.rvFavorite
        favoritAdapter = FavoritAdapter { favoriteUser ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("username", favoriteUser.username)
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritAdapter
        }

        favoriteViewModel.favoriteUser.observe(viewLifecycleOwner, Observer { favoriteUsers ->
            favoritAdapter.submitList(favoriteUsers)
        })


    }


}