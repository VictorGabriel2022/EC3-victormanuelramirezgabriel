package com.example.ec3.fragmnets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ec3.CardAdapter
import com.example.ec3.R
import com.example.ec3.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private  lateinit var viewModel: Cardfavoriteviewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(Cardfavoriteviewmodel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CardAdapter(listOf()){cards->
            val couponDetailDirection = FavoriteFragmentDirections.actionFavoriteFragment2ToDetailsCardFragment(cards)
            findNavController().navigate(couponDetailDirection)

        }
        binding.recyclerView.adapter =  adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),1,
            RecyclerView.VERTICAL, false)
        viewModel.favorites.observe(requireActivity()){
            adapter.cards = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getFavorites()
    }
}