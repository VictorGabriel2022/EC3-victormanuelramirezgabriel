package com.example.ec3.fragmnets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ec3.R
import com.example.ec3.databinding.FragmentDetailsCardBinding
import com.example.ec3.model.CardDetails


class DetailsCardFragment : Fragment() {
    private lateinit var binding: FragmentDetailsCardBinding
    val args: DetailsCardFragmentArgs by navArgs()
    private lateinit var  cardDetails: CardDetails
    private lateinit var viewModel: CardDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardDetails = args.carddetails
        viewModel = ViewModelProvider(requireActivity()).get(CardDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsCardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameTextView.text = cardDetails.title
        binding.levelTextView.text = cardDetails.short_description
        binding.atkDefTextView.text = cardDetails.game_url
        binding.typeTextView.text = cardDetails.genre
        // Cargar la imagen utilizando Glide
        Glide.with(binding.root)
            .load(cardDetails.thumbnail) // URL de la imagen
            .into(binding.cardImageView)
        binding.button3.apply {
            visibility=if(cardDetails.isFavorite) View.GONE else View.VISIBLE
        }
        binding.favorito.apply {
            visibility=if(cardDetails.isFavorite) View.VISIBLE else View.GONE
        }
        binding.favorito.setOnClickListener {
            cardDetails.isFavorite = false
            viewModel.removeFavorite(cardDetails.id)

            // Ocultar el botón "Quitar de favorito"
            binding.favorito.visibility = View.GONE

            // Mostrar el botón "Añadir a favoritos"
            binding.button3.visibility = View.VISIBLE
        }
        binding.button3.setOnClickListener {
            cardDetails.isFavorite =true
            viewModel.addFavorite(cardDetails)
        }
    }

}
















