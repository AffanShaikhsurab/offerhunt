package com.example.oncash.DataType

import kotlinx.serialization.Serializable

@Serializable
data class ClaimedOffer(
    val OfferId :Int,
    val UserId :Int,
    val ClaimedDate : String
)