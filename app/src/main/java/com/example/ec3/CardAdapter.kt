package com.example.ec3
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ec3.R
import com.example.ec3.model.Card

class CardAdapter(private var cardList: List<Card>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        private val descTextView: TextView = itemView.findViewById(R.id.descTextView)
        private val atkDefTextView: TextView = itemView.findViewById(R.id.atkDefTextView)
        private val levelTextView: TextView = itemView.findViewById(R.id.levelTextView)
        private val cardImageView: ImageView = itemView.findViewById(R.id.cardImageView)

        fun bind(card: Card) {
            nameTextView.text = card.name
            typeTextView.text = card.type
            descTextView.text = card.desc
            atkDefTextView.text= card.atk.toString()
            levelTextView.text= card.level.toString()

            Glide.with(itemView)
                .load(card.card_images[0].image_url)
                .placeholder(R.drawable.logoyugioh)
                .error(R.drawable.logoyugioh)
                .into(cardImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcard, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun setData(newCardList: List<Card>) {
        cardList = newCardList
        notifyDataSetChanged()
    }
}




