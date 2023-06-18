package com.example.oncash.ViewModel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.oncash.Component.offerHistory_component
import com.example.oncash.Repository.Info_FirebaseRepo
import com.example.oncash.DataType.Instruction
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.Offer_Information
import com.example.oncash.DataType.userData
import kotlinx.coroutines.launch

class info_viewModel : ViewModel() {

    val ImagesList : MutableLiveData<ArrayList<String>> = MutableLiveData()

    fun getImages(offerId: String , category :String) : MutableLiveData<ArrayList<String>>{
        viewModelScope.launch{
            ImagesList.value = Info_FirebaseRepo().getInstructionList(offerId , category)
        }

        return ImagesList
    }

    fun getOffers(offerId: String , category :String) : MutableLiveData<ArrayList<String>>{
        viewModelScope.launch{
            ImagesList.value = Info_FirebaseRepo().getOffers(offerId , category)
        }

        return ImagesList
    }
    fun updateOfferHistory(user : userData , offerId: String , offerPrice:String , offerName : String){
        viewModelScope.launch {
            offerHistory_component().updateAirtable(user , offerId , offerPrice , offerName)
        }
    }

}