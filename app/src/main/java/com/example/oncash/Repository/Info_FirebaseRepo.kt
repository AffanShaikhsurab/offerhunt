package com.example.oncash.Repository

import android.util.Log
import com.example.oncash.DataType.Instruction
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.Offer_Information
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Info_FirebaseRepo {

   suspend fun getInstructionList( placeId :String  , category: String) :ArrayList<String>  = withContext(Dispatchers.IO){
        val response : ArrayList<String> = ArrayList()
        val data : DatabaseReference = FirebaseDatabase.getInstance().getReference(category).child(placeId).child("ImagesList")
       try {
           data.get().await().children.map { snapShot ->
               val image = snapShot.value as String
               response.add(image)
           }
       } catch (_: Exception) {

       }

       return@withContext response


    }


    suspend fun getOffers( placeId :String  , category: String) :ArrayList<String>  = withContext(Dispatchers.IO){
        val response : ArrayList<String> = ArrayList()
        val data : DatabaseReference = FirebaseDatabase.getInstance().getReference(category).child(placeId).child("Offers")
        try {
            data.get().await().children.map { snapShot ->
                val image = snapShot.value as String
                response.add(image)
            }
        } catch (_: Exception) {

        }

        return@withContext response


    }




}