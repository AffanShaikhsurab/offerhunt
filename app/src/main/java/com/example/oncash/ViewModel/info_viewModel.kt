package com.example.oncash.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.DataType.OfferList
import com.example.oncash.DataType.PlaceImages
import com.example.oncash.DataType.userData
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import kotlinx.coroutines.launch

class info_viewModel : ViewModel() {

    val ImagesList : MutableLiveData<PlaceImages> = MutableLiveData()
    val OfferList : MutableLiveData<OfferList> = MutableLiveData()
    fun getImages(placeid: Int ) : MutableLiveData<PlaceImages>{
        viewModelScope.launch{
            ImagesList.value = UserInfo_Airtable_Repo().getImages(placeid)
        }
        return ImagesList
    }

    fun getOffers(offerId: Int) : MutableLiveData<OfferList>{
        viewModelScope.launch{
            OfferList.value = UserInfo_Airtable_Repo().getOffers(offerId)
        }
        return OfferList
    }
    fun updateOfferHistory(user : userData , offerId: String , offerPrice:String , offerName : String){
        viewModelScope.launch {
            offerHistory_component().updateAirtable(user , offerId , offerPrice , offerName)
        }
    }

}