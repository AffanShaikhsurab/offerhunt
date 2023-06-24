package com.example.oncash.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.DataType.*
import com.example.oncash.DataType.SerializedDataType.OfferHistory.OfferHistoryRecord
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import io.ktor.http.*
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
class home_viewModel : ViewModel() {

    private val wallet : MutableLiveData<walletDatatype> = MutableLiveData()
    private val userData : MutableLiveData<userData> = MutableLiveData()

    val category : Int = 1

    //wallet
    private val withdrawalTransaction : MutableLiveData<ArrayList<withdrawalTransaction>> = MutableLiveData()



    // wallet
    // weekly offer viewmodel
    private val offerList : MutableLiveData<PlacesList> = MutableLiveData()

    fun getOfferList(category : Int) : MutableLiveData<PlacesList> {
        viewModelScope.launch {
            offerList.postValue(UserInfo_Airtable_Repo().getPlaces(category))
        }
        return offerList
    }
//offer history viewmodel
private val offerhistoryList : MutableLiveData<ArrayList<OfferHistoryRecord>> = MutableLiveData()
    fun getOffersHistory(userId:Long){
        viewModelScope.launch {
            offerhistoryList.postValue(offerHistory_component().getOfferHIstory(userId = userId))
        }
    }

    fun getOfferHistoryList():MutableLiveData<ArrayList<OfferHistoryRecord>>{
        return offerhistoryList
    }

//home viewmodel


    fun setUserData(user : userData){
        userData.postValue( user )
    }



    fun getuserData():MutableLiveData<userData>{
        return userData
    }

}