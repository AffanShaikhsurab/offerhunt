package com.example.oncash.DataType

import com.example.oncash.Fragment.weeklyOffers
import kotlinx.serialization.Serializable

@Serializable
data class OfferList(val Offers : ArrayList<Offer>)