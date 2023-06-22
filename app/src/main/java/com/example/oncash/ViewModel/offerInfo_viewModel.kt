package com.example.oncash.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.DataType.Offer
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class offerInfo_viewModel  : ViewModel() {

    val offer_info : MutableLiveData<Offer> = MutableLiveData()
    val status : MutableLiveData<Int>  = MutableLiveData()
    fun setOffer( userId : Int , offerId :Int){
        viewModelScope.launch {
            status.value = UserInfo_Airtable_Repo().setOffer(offerId, userId)
        }
    }
}