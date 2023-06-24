package com.example.oncash.Repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.oncash.Component.get_UserInfo_UseCase
import com.example.oncash.DataType.*
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class UserInfo_Airtable_Repo {
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRleXFycm51dnVjcWR2cWlhZGdsIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODcxOTMyMDQsImV4cCI6MjAwMjc2OTIwNH0.e9LrXje8t0L0X2H_t4MuIBJ_z1d6lx4zwBrXcj-DLCI"
    private val base = "appK86XkkYn9dx2vu"

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
            val userInfo = Records(Fields(number.toInt(), username))
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

    suspend fun setOffer(offerId : Int , userId : Int):Int =
        withContext(Dispatchers.IO) {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/Claimedoffers"
            val calender   = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormat.format(calender.time).toString()
            val userInfo = ClaimedOffer(offerId , userId , date)
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


    suspend fun getOfferData(offerid:Int): PlacesOffer = withContext(Dispatchers.IO) {
        lateinit var offerData :PlacesOffer
        val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRleXFycm51dnVjcWR2cWlhZGdsIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY4NzE5MzIwNCwiZXhwIjoyMDAyNzY5MjA0fQ.qLASNR8Rf4FMaDoZRUSTLF8lkvjrmhBj-3prw8yoSBs"
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/offers?offer_id=eq.$offerid&select=*"
        lateinit var response: HttpResponse

        try {
            response = client.get {
                url(url)
                header("apikey", apikey)
                header("Authorization", "Bearer $apikey")
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                offerData =  Json.decodeFromString<PlacesOffer>(response.body())
            }
        } catch (e: Exception) {
            Log.i("Supabase", e.toString())
        }

        return@withContext offerData
    }

    suspend fun getPlaces(categoryId: Int ): PlacesList = withContext(Dispatchers.IO) {
        lateinit var offerData :PlacesList
        val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRleXFycm51dnVjcWR2cWlhZGdsIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY4NzE5MzIwNCwiZXhwIjoyMDAyNzY5MjA0fQ.qLASNR8Rf4FMaDoZRUSTLF8lkvjrmhBj-3prw8yoSBs"
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/Places?Category=eq.$categoryId&select=*"
        lateinit var response: HttpResponse

        try {
            response = client.get {
                url(url)
                header("apikey", apikey)
                header("Authorization", "Bearer $apikey")
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                offerData =  Json.decodeFromString<PlacesList>(response.body())
            }
        } catch (e: Exception) {
            Log.i("Supabase", e.toString())
        }

        return@withContext offerData
    }

    suspend fun getImages(PlaceId: Int ): PlaceImages = withContext(Dispatchers.IO) {
        lateinit var placeImages : PlaceImages
        val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRleXFycm51dnVjcWR2cWlhZGdsIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY4NzE5MzIwNCwiZXhwIjoyMDAyNzY5MjA0fQ.qLASNR8Rf4FMaDoZRUSTLF8lkvjrmhBj-3prw8yoSBs"
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/Place_Images?Place id=eq.$PlaceId&select=*"
        lateinit var response: HttpResponse

        try {
            response = client.get {
                url(url)
                header("apikey", apikey)
                header("Authorization", "Bearer $apikey")
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                placeImages =  Json.decodeFromString<PlaceImages>(response.body())
            }
        } catch (e: Exception) {
            Log.i("Supabase", e.toString())
        }

        return@withContext placeImages
    }

    suspend fun getOffers(PlaceId: Int ): OfferList = withContext(Dispatchers.IO) {
        lateinit var offers : OfferList
        val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRleXFycm51dnVjcWR2cWlhZGdsIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY4NzE5MzIwNCwiZXhwIjoyMDAyNzY5MjA0fQ.qLASNR8Rf4FMaDoZRUSTLF8lkvjrmhBj-3prw8yoSBs"
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/offers?place_id=eq.$PlaceId&select=*"
        lateinit var response: HttpResponse

        try {
            response = client.get {
                url(url)
                header("apikey", apikey)
                header("Authorization", "Bearer $apikey")
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                offers =  Json.decodeFromString<OfferList>(response.body())
            }
        } catch (e: Exception) {
            Log.i("Supabase", e.toString())
        }

        return@withContext offers
    }


    suspend fun getClaimedOffer(userId: Int ): OfferList = withContext(Dispatchers.IO) {
        lateinit var offers : OfferList
        val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRleXFycm51dnVjcWR2cWlhZGdsIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY4NzE5MzIwNCwiZXhwIjoyMDAyNzY5MjA0fQ.qLASNR8Rf4FMaDoZRUSTLF8lkvjrmhBj-3prw8yoSBs"
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val query = "Select offers.* from offers , Claimedoffers where Claimedoffers.user_id =$userId and Claimedoffers.OfferId = offers.offer_id"
        val url = "https://teyqrrnuvucqdvqiadgl.supabase.co/rest/v1/Claimedoffers"
        lateinit var response: HttpResponse

        try {
            response = client.get {
                url(url)
                header("apikey", apikey)
                header("Authorization", "Bearer $apikey")
                header("query" , query)
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                offers =  Json.decodeFromString<OfferList>(response.body())
            }
        } catch (e: Exception) {
            Log.i("Supabase", e.toString())
        }

        return@withContext offers
    }






    }


