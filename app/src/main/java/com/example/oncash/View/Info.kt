package com.example.oncash.View

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.oncash.Component.RecylerviewAdapter.PlacesImage_RecylerviewAdapter
import com.example.oncash.Component.RecylerviewAdapter.PlacesOffer_RecylerviewAdapter
import com.example.oncash.ViewModel.info_viewModel
import com.example.oncash.ViewModel.offerInfo_viewModel
import com.example.oncash.databinding.ActivityInfoBinding


class Info : AppCompatActivity() {
     lateinit var binding : ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Getting Data from the intent form home(Activity)
        val offerName= intent.getStringExtra("PlaceName")
        binding.offernameInfo.text = offerName
        val placeId= intent.getStringExtra("PlaceId")!!.toInt()
        val category= intent.getStringExtra("Category")
        var offerAddress= intent.getStringExtra("PlaceAddress")
        binding.offerAddress.text = offerAddress

        val banner : String ? = intent.getStringExtra("PlaceBanner")

        if(banner.isNullOrEmpty()){
            Glide.with(this).load(banner).into(binding.offerImageInfo)
        }
 // Initilizing the recylerview adapter
        val adapter = PlacesImage_RecylerviewAdapter()
        binding.placeImages.adapter = adapter
        binding.placeImages.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)

        val offerViewModel :offerInfo_viewModel by viewModels()
        val Offeradapter = PlacesOffer_RecylerviewAdapter(offerViewModel)
        binding.placeOfferImage.adapter = Offeradapter
        binding.placeOfferImage.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)

        //Observing the getInstructionList() in info_viewmodel (ie which gets the data from info_FirebaseRepo)
        val info_viewModel : info_viewModel by viewModels()

        info_viewModel.getImages(placeId!!).observe(this , Observer {
            if (it.Images.isNotEmpty()){
                adapter.updateList(it.Images)
            }
        })

        info_viewModel.getOffers(placeId!!).observe(this , Observer {
            if (it.Offers.isNotEmpty()){
                Offeradapter.updateList(it.Offers)
            }
        })






    }
}