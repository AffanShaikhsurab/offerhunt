package com.example.oncash.Component

import com.example.oncash.Repository.UserInfo_Airtable_Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class get_UserInfo_UseCase {


    suspend fun registerUser(userNumber: Long , username:String) : Int = withContext(Dispatchers.Default) {

        return@withContext UserInfo_Airtable_Repo().createUser(userNumber, username)
    }



}