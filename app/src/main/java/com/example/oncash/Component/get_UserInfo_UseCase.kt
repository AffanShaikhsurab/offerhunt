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


    suspend fun loginManager(userNumber: Long): UserData1 = withContext(Dispatchers.Default)
    {
        val userData: UserData1 = isUserRegistered(userNumber)

        if (!userData.isUserRegistered) {
            return@withContext UserData1(true, registerUser(userNumber))
        }
        return@withContext userData
    }

    suspend fun isUserRegistered(userNumber: Long): UserData1 = withContext(Dispatchers.Default) {

        val users: JSONArray? = UserInfo_Airtable_Repo().getUserInfo().value!!
        var isUserRegistered: Boolean = false
        lateinit var userRecordId: String
        for (i in 0 until users!!.length()) {

            userRecordId = JSONObject(users[i]!!.toString()).getString("id").toString()

            val user = JSONObject(users[i]!!.toString()).getString("fields")
            val phone = JSONObject(user).getString("UserPhone")
            if (phone.toLong() == userNumber) {
                isUserRegistered = true
                return@withContext UserData1(isUserRegistered, userRecordId)

            }
        }

        return@withContext UserData1(isUserRegistered, userRecordId)
    }

    private suspend fun registerUser(userNumber: Long): String = withContext(Dispatchers.Default) {

        return@withContext UserInfo_Airtable_Repo().createUser(userNumber, 0 , 0)
    }


    suspend fun getuserWithdrwalHistory(userNumber: Long): ArrayList<withdrawalTransaction> = withContext(Dispatchers.Default) {

        val list : ArrayList<withdrawalTransaction> = ArrayList()
        val withdrawalTransaction: JSONArray? = UserInfo_Airtable_Repo().getWithdrawTransaction().value!!
        lateinit var createdTime: String
        for (i in 0 until withdrawalTransaction!!.length()) {
            createdTime = JSONObject(withdrawalTransaction[i]!!.toString()).getString("createdTime").toString()
            val user = JSONObject(withdrawalTransaction[i]!!.toString()).getString("fields")
            val requestedAmount = JSONObject(user).getString("RequestedAmount")
            val phone = JSONObject(user).getString("UserNumber")
            val status = JSONObject(user).getString("Status")
            if (phone.toLong() == userNumber) {
               val date : String = createdTime.split("T")[0]

                list.add( withdrawalTransaction(date , requestedAmount ,  status ))
            }
        }

        return@withContext list
    }

}