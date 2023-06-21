package com.example.oncash.DataType

import kotlinx.serialization.Serializable

@Serializable
data class PlacesOffer(
    val OfferId :Int,
    val ResturantId :Int,
    val Discount : Int ,
    val StartDate : String,
    val EndDate : String
)