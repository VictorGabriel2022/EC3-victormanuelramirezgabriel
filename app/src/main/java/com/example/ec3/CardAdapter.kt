package com.example.ec3
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ec3.databinding.ItemcardBinding
import com.example.ec3.model.CardDetails

class CardAdapter(var cards: List<CardDetails>,val onClick:(CardDetails)-> Unit): RecyclerView.Adapter<CouponVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponVH {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coupon,parent,false)
        val binding = ItemcardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CouponVH(binding , onClick)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CouponVH, position: Int) {
        holder.bind(cards[position])
    }

}

class CouponVH(private val binding: ItemcardBinding, val onClick: (CardDetails) -> Unit): RecyclerView.ViewHolder(binding.root){
    fun bind(cardDetails: CardDetails) {

        binding.nameTextView.text = cardDetails.title
        binding.typeTextView.text = cardDetails.genre
        binding.atkDefTextView.text = cardDetails.platform
        binding.levelTextView.text = cardDetails.publisher

        // Cargar la imagen utilizando Glide
        Glide.with(binding.root)
            .load(cardDetails.thumbnail) // URL de la imagen
            .into(binding.cardImageView)
        binding.root.setOnClickListener{
            onClick(cardDetails)
        }

    }

}
