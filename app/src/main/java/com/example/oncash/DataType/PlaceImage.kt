package com.example.oncash.DataType

import kotlinx.serialization.Serializable

@Serializable
data class PlaceImage(
    val id :Int,
    val PlaceId :Int,
    val Image :String
)