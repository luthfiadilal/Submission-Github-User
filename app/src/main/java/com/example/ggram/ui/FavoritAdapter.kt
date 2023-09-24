package com.example.ggram.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ggram.database.FavoriteUser
import com.example.ggram.databinding.ItemUsersSearchBinding

class FavoritAdapter(private val onItemClick: (FavoriteUser) -> Unit) : ListAdapter<FavoriteUser, FavoritAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
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
        fun bind(item: FavoriteUser) {
            binding.tvUsers.text = item.username
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.imgUser)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteUser> =
            object : DiffUtil.ItemCallback<FavoriteUser>() {
                override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                    return oldItem == newItem
                }
            }
    }

}