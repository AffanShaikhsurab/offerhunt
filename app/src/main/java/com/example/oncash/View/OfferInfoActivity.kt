package com.example.oncash.View


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.oncash.Component.UserDataStoreUseCase
import com.example.oncash.DataType.Offer
import com.example.oncash.R
import com.example.oncash.ViewModel.offerInfo_viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfferInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_info)

        val viewmodel : offerInfo_viewModel by viewModels()
        lateinit var offer : Offer
        viewmodel.offer_info.observe(this){
            offer = it
        }
        val avail_offer : Button = findViewById(R.id.avail_offer)
        var userNumber : Int = 0
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                userNumber  =  UserDataStoreUseCase().retrieveUserNumber(this@OfferInfoActivity)
            }
        }
        avail_offer.setOnClickListener{
            if(userNumber!=0)
            {
                viewmodel.setOffer(offer.OfferId , userNumber)
            }
            startActivity(Intent(this , QrCode::class.java))
        }

    }



}