package com.example.oncash.Repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.oncash.Component.get_UserInfo_UseCase
import com.example.oncash.DataType.*
import com.example.oncash.DataType.SerializedDataType.OfferHistory.OfferHistoryRecord
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.reflect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class UserInfo_Airtable_Repo {
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRleXFycm51dnVjcWR2cWlhZGdsIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODcxOTMyMDQsImV4cCI6MjAwMjc2OTIwNH0.e9LrXje8t0L0X2H_t4MuIBJ_z1d6lx4zwBrXcj-DLCI"
    private val base = "appK86XkkYn9dx2vu"




    suspend fun getUserInfo(): MutableLiveData<JSONArray> = withContext(Dispatchers.IO) {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val tableId = "tblOwifipGGANDJPN"
        val url = "https://api.airtable.com/v0/$base/$tableId/"
        val users: MutableLiveData<JSONArray> = MutableLiveData()
        try {
            val response = client.get(url) {
                parameter(
                    "api_key", apiKey
                )
            }


            users.postValue(JSONArray(JSONObject(response.body<String>()).getString("records")))
        } catch (e: Exception) {
            users.postValue(null)
        }
        return@withContext users

    }



    suspend fun createUser(number: Long, username :String):Int =
        withContext(Dispatchers.IO) {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/User/"
            val userInfo = Records(Fields(number, username))
            lateinit var response : HttpResponse

            try {

                 response = client.post {
                    url(url)
                    header("apikey", apiKey)
                    header("Authorization", "Bearer $apiKey")
                    contentType(ContentType.Application.Json)
                    setBody(userInfo)
                }
            } catch (e: Exception) {
                Log.i("airtable", e.toString())
            }

           return@withContext response!!.status.value

        }





   suspend fun updateOfferHistory(userData: userData ,offerId: String , offerPrice:String , offerName : String) = withContext(Dispatchers.IO){

       val base = "appK86XkkYn9dx2vu"
       val tableId = "tblGyiEF9F9HpGuv2"
       val url = "https://api.airtable.com/v0/$base/$tableId/"
        val client = HttpClient(CIO){
            install(ContentNegotiation){
                Gson()
                json(
                    Json{
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }
        }

       val offerHistory =  OfferHistoryRecord(com.example.oncash.DataType.SerializedDataType.OfferHistory.Fields(userData.userRecordId , offerId ,  "Being Reviewed" , offerPrice ,offerName ))

       Log.i("offerhistory" , "userid"+userData.userRecordId)
       Log.i("offerhistory" , thereExists(getOfferHistory() , offerHistory).toString())
       Log.i("offerhistory" , getOfferHistory().toString())

       val status =  client.post {
           url(url)
           header("Authorization", "Bearer $apiKey")
           contentType(ContentType.Application.Json)
           setBody(offerHistory)
       }
       Log.i("offerhistory" , status.status.value.toString())
   }

    suspend fun getOfferHistory() : ArrayList<OfferHistoryRecord> = withContext(Dispatchers.IO){


        val base = "appK86XkkYn9dx2vu"
        val tableId = "tblGyiEF9F9HpGuv2"
        val url = "https://api.airtable.com/v0/$base/$tableId/"
        val client = HttpClient(CIO){
            install(ContentNegotiation){
                Gson()
                json(
                    Json{
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }
        }

        val response = client.get(url) {
            parameter(
                "api_key", apiKey
            ) }
        val type = object : TypeToken<ArrayList<OfferHistoryRecord>>(){}.type
        val jsonObject = JSONArray(JSONObject(response.body<String>()).getString("records"))
        return@withContext Gson().fromJson( jsonObject.toString(), type )
    }

    suspend fun thereExists( list:kotlin.collections.ArrayList<OfferHistoryRecord> , element :OfferHistoryRecord):Boolean = withContext(Dispatchers.IO){
        var thereExisit = false
        for (current_element in list){
            if (current_element.fields.UserId == element.fields.UserId && current_element.fields.OfferId == element.fields.OfferId ){
                thereExisit = true
            }
        }
        return@withContext thereExisit
    }



    }


