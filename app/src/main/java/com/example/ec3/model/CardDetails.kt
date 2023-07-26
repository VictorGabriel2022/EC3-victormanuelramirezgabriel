package com.example.ec3.model

data class CardDetails(val data: List<Card>)

data class Card(
    val id: Int,
    val name: String,
    val type: String,
    val frameType: String,
    val desc: String,
    val atk: Int,
    val def: Int,
    val level: Int,
    val race: String,
    val attribute: String,
    val archetype: String,
    val card_sets: List<SetInfo>,
    val card_images: List<ImageInfo>,
    val card_prices: List<PriceInfo>,

)

data class SetInfo(
    val set_name: String,
    val set_code: String,
    val set_rarity: String,
    val set_rarity_code: String,
    val set_price: String
)

data class ImageInfo(
    val id: Int,
    val image_url: String,
    val image_url_small: String,
    val image_url_cropped: String
)

data class PriceInfo(
    val cardmarket_price: String,
    val tcgplayer_price: String,
    val ebay_price: String,
    val amazon_price: String,
    val coolstuffinc_price: String
)

