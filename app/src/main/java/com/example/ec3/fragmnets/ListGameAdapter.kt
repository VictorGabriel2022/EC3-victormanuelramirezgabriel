package com.example.ec3.fragmnets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ec3.databinding.ItemcardBinding
import com.example.ec3.model.ListGame

class ListGameAdapter(private val listGames: List<ListGame>) : RecyclerView.Adapter<ListGameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listGame = listGames[position]
        holder.bind(listGame)
    }

    override fun getItemCount(): Int {
        return listGames.size
    }

    inner class ViewHolder(private val binding: ItemcardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listGame: ListGame) {
            binding.nameTextView.text = listGame.title
            binding.typeTextView.text =listGame.description
            Glide.with(binding.root)
                .load(listGame.imageUrl) // URL de la imagen
                .into(binding.cardImageView)
            // Load image using Glide or other image loading library
            // Example: Glide.with(itemView).load(listGame.imageUrl).into(binding.couponImage)
        }
    }
}
