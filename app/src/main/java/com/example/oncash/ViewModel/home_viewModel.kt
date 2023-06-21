package com.example.oncash.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.Component.UserDataStoreUseCase
import com.example.oncash.Component.get_UserInfo_UseCase
import com.example.oncash.Component.offerHistory_component
import com.example.oncash.Component.sortingComponent
import com.example.oncash.DataType.*
import com.example.oncash.DataType.SerializedDataType.OfferHistory.OfferHistoryRecord
import com.example.oncash.Repository.Offer_FIrebase
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import com.example.oncash.Repository.offer_AirtableDatabase
import com.example.oncash.RoomDb.userDb
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

    fun getUserData(context:Context) {
        viewModelScope.launch {
          userData.postValue( userData(UserDataStoreUseCase().retrieveUsername(context) ,  UserDataStoreUseCase().retrieveUserNumber(context)) )
        }
    }

    fun getuserData():MutableLiveData<userData>{
        return userData
    }

}