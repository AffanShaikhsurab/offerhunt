package com.example.oncash.Component

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.oncash.DataType.UserData1
import com.example.oncash.DataType.withdrawalTransaction
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

class get_UserInfo_UseCase {


    suspend fun registerUser(userNumber: Long , username:String) : Int = withContext(Dispatchers.Default) {

        return@withContext UserInfo_Airtable_Repo().createUser(userNumber, username)
    }



}