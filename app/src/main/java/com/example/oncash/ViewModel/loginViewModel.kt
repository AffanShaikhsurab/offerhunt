package com.example.oncash.ViewModel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.Component.get_UserInfo_UseCase
import com.example.oncash.DataType.UserData1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class loginViewModel:ViewModel() {
    private  var reponse :MutableLiveData<Int> = MutableLiveData()

    fun addUser(userNumber : Long , username :String ) : MutableLiveData<Int> {

        viewModelScope.launch {
            withContext(Dispatchers.Main) {
              reponse.value  =  get_UserInfo_UseCase().registerUser(userNumber , username)
            }
        }
        return reponse
    }




}