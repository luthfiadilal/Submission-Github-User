package com.example.ggram.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggram.R
import com.example.ggram.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var followingAdapter: FollowingsAdapter
    private lateinit var binding : FragmentFollowingBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        followingAdapter = FollowingsAdapter()

        binding.followingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.followingsRecyclerView.adapter = followingAdapter

        progressBar = binding.progressBar

        progressBar.visibility = View.VISIBLE

        // Observe the following data from ViewModel
        viewModel.followingLiveData.observe(viewLifecycleOwner) { following ->
            followingAdapter.submitList(following)
            progressBar.visibility = View.GONE
        }
    }
}