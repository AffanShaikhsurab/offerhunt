package com.example.oncash.View

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.oncash.Component.PlacesImage_RecylerviewAdapter
import com.example.oncash.Component.PlacesOffer_RecylerviewAdapter
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.userData
import com.example.oncash.Repository.offer_AirtableDatabase
import com.example.oncash.ViewModel.info_viewModel
import com.example.oncash.databinding.ActivityInfoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener


class Info : AppCompatActivity() {
     lateinit var binding : ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Getting Data from the intent form home(Activity)
        val offerName= intent.getStringExtra("OfferName")
        binding.offernameInfo.text = offerName
        val placeId= intent.getStringExtra("PLaceId")
        val category= intent.getStringExtra("Category")
        var offerAddress= intent.getStringExtra("OfferAddress")
        binding.offerAddress.text = offerAddress
        Glide.with(this).load(intent.getStringExtra("OfferImage")).into(binding.offerImageInfo)
 // Initilizing the recylerview adapter
        val adapter = PlacesImage_RecylerviewAdapter()
        binding.placeImages.adapter = adapter
        binding.placeImages.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)

        val Offeradapter = PlacesOffer_RecylerviewAdapter()
        binding.placeOfferImage.adapter = Offeradapter
        binding.placeOfferImage.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)

        //Observing the getInstructionList() in info_viewmodel (ie which gets the data from info_FirebaseRepo)
        val info_viewModel : info_viewModel by viewModels()

        info_viewModel.getImages(placeId!! , category!!).observe(this , Observer {
            if (it.isNotEmpty()){
                adapter.updateList(it)
            }
        })

        info_viewModel.getOffers(placeId!! , category!!).observe(this , Observer {
            if (it.isNotEmpty()){
                Offeradapter.updateList(it)
            }
        })






    }
}