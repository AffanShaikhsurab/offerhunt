package com.example.oncash.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oncash.DataType.Offer

class offerInfo_viewModel  : ViewModel() {

    val offer_info : MutableLiveData<Offer> = MutableLiveData()
}