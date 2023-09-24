package com.example.ggram.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ggram.data.response.ItemsItem
import com.example.ggram.databinding.ItemUsersBinding
import com.example.ggram.databinding.ItemUsersSearchBinding

class ListUserAdapterSearch(private val onItemClick: (ItemsItem) -> Unit) : ListAdapter<ItemsItem, ListUserAdapterSearch.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    class MyViewHolder(val binding: ItemUsersSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            binding.tvUsers.text = item.login
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.imgUser)
        }
    }



    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

        }
    }


}