package com.example.oncash.DataType

import kotlinx.serialization.Serializable

@Serializable
data class Places(
    val PlacesId :Int,
    val Name :String,
    val Address : String ,
    val Category : Int,
    val Banner : String?
)