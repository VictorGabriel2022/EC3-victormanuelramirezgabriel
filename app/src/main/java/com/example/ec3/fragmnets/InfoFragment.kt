package com.example.ec3.fragmnets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ec3.databinding.FragmentInfoBinding
import com.example.ec3.model.ListGame
import com.google.firebase.firestore.FirebaseFirestore


class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var listGameAdapter: ListGameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()

        val listGamesList = mutableListOf<ListGame>()
        listGameAdapter = ListGameAdapter(listGamesList)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listGameAdapter
        }

        // Load your listGames from Firestore and add them to listGamesList
        firestore.collection("Listagames")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val title = document.getString("title") ?: ""
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val description= document.getString("description") ?: ""
                    val listGame = ListGame(title, imageUrl,description)
                    listGamesList.add(listGame)
                }
                listGameAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }
    }
}