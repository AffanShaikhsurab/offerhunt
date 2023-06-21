package com.example.oncash.DataType

import kotlinx.serialization.Serializable

@Serializable
data class PlacesList(
    val Places: ArrayList<Places>
)