package com.example.oncash.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oncash.DataType.OfferList

class offer_viewmodel : ViewModel() {
    private val offerList : MutableLiveData<OfferList> = MutableLiveData()

//    fun getOfferList() : MutableLiveData<OfferList>{
//        viewModelScope.launch{
//            offerList.postValue( UserInfo_Airtable_Repo().)
//            offer_AirtableDatabase().getData()
//        }
//        return offerList
//    }
//

}