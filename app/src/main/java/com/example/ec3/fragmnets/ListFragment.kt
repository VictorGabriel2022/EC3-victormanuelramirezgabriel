package com.example.ec3.fragmnets
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.ec3.CardAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.example.ec3.databinding.FragmentListBinding


class ListFragment : Fragment() {
             private lateinit var binding: FragmentListBinding
             private lateinit var viewModel: CardListViewModel

             override  fun onCreate(savedInstanceState:Bundle?){
                 super.onCreate(savedInstanceState)
                 viewModel = ViewModelProvider(requireActivity()).get(CardListViewModel::class.java)
             }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CardAdapter(listOf()){cards ->
            val couponDetailDirection = ListFragmentDirections.actionListFragmentToDetailsCardFragment(cards)
            findNavController().navigate(couponDetailDirection)

        }
        binding.recyclerView.adapter =  adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),1,RecyclerView.VERTICAL, false)
        viewModel.cardList.observe(requireActivity()){
            adapter.cards = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getCouponsFromService()

    }


}




















