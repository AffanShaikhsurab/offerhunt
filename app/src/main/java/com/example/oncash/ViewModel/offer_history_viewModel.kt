package com.example.oncash.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.Component.offerHistory_component
import com.example.oncash.DataType.OfferList
import com.example.oncash.DataType.SerializedDataType.OfferHistory.OfferHistoryRecord
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import kotlinx.coroutines.launch

class offer_history_viewModel: ViewModel() {
   private val clamiedOffers : MutableLiveData<OfferList> = MutableLiveData()
    fun getOffersHistory(userId:Int){
        viewModelScope.launch {
            clamiedOffers.postValue(UserInfo_Airtable_Repo().getClaimedOffer(userId = userId))
        }
    }
    fun getClamiedOffers():MutableLiveData<OfferList>{
        return clamiedOffers
    }

}