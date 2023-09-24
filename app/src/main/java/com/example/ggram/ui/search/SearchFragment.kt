package com.example.ggram.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggram.R
import com.example.ggram.data.response.ItemsItem
import com.example.ggram.data.response.UserResponse
import com.example.ggram.data.retrofit.ApiConfig
import com.example.ggram.databinding.FragmentSearchBinding
import com.example.ggram.ui.DetailActivity
import com.example.ggram.ui.ListUserAdapter
import com.example.ggram.ui.ListUserAdapterSearch
import com.example.ggram.ui.home.HomeFragment
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: ListUserAdapterSearch

    companion object {
        private const val TAG = "SearchFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val recyclerView = binding.rvSearchResults

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

        viewModel.findUsers("luthfi")
        adapter = ListUserAdapterSearch { userItem ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("username", userItem.login) 
            startActivity(intent)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val searchText = searchView.text.toString()
                    searchBar.text = searchText
                    searchView.hide()
                    viewModel.findUsers(searchText)
                    false
                }

            recyclerView.adapter = adapter
        }

        viewModel.usersData.observe(viewLifecycleOwner) { users ->
            setUsersData(users)
        }

        return binding.root

    }

    private fun setUsersData(users: List<ItemsItem>) {
        adapter.submitList(users)
        Log.d(TAG, "user : $users")
    }
}