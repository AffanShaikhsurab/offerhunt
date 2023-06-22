package com.example.oncash.Component

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private val Context.dataStore by preferencesDataStore(
    name = "UserData"
)
class UserDataStoreUseCase {

    suspend fun retrieveUser(context: Context): Boolean ? = withContext(Dispatchers.IO){
        return@withContext context.dataStore.data.first()[booleanPreferencesKey("isUserLogin")]
    }

    suspend fun retrieveUserNumber(context: Context): Int = withContext(Dispatchers.IO){
        return@withContext context.dataStore.data.first()[intPreferencesKey("userNumber")]!!
    }

    suspend fun retrieveUsername(context: Context): String = withContext(Dispatchers.IO){
        return@withContext context.dataStore.data.first()[stringPreferencesKey("username")]!!
    }

    suspend fun storeUser(context: Context, bool: Boolean, userNumber: Int , userRecordId:String) = withContext(Dispatchers.IO){
      context.dataStore.edit{
          it[intPreferencesKey("userNumber")] = userNumber
          it[booleanPreferencesKey("isUserLogin")] = bool
          it[stringPreferencesKey("username")] = userRecordId
      }
    }
}
