package com.example.ggram.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ggram.R
import com.example.ggram.databinding.FragmentFollowersBinding
import com.example.ggram.databinding.ItemUsersSearchBinding


class FollowersFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        followersAdapter = FollowersAdapter()

        binding.followersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.followersRecyclerView.adapter = followersAdapter

        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE


        viewModel.followersLiveData.observe(viewLifecycleOwner) { followers ->
            followersAdapter.submitList(followers)
            progressBar.visibility = View.GONE

        }



    }
}