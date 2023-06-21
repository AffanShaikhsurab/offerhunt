package com.example.oncash.DataType

import kotlinx.serialization.Serializable

@Serializable
data class PlaceImages(
    val Images: ArrayList<PlaceImage>
)