package com.example.oncash.DataType

import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val OfferId: Int,
    val PlaceId: Int,
    val Discount: Int,
    val StartDate: String,
    val EndDate: String,
    val OfferImage :String
)