package com.example.oncash.View

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.oncash.DataType.OfferList
import com.example.oncash.DataType.userData
import com.example.oncash.R
import com.example.oncash.RoomDb.User
import com.example.oncash.RoomDb.userDb
import com.example.oncash.ViewModel.home_viewModel
import com.example.oncash.databinding.ActivityHomeBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : AppCompatActivity() {
     lateinit var binding: ActivityHomeBinding
    val homeViewmodel: home_viewModel by viewModels()
    lateinit var OfferList : OfferList
    private  var userData: userData = userData("",0)
    lateinit var roomDb:userDb
    private lateinit var  Offer_recycler:RecyclerView
    private lateinit var dbref: DatabaseReference
    val offer_list:MutableList<offer> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            roomDb = Room.databaseBuilder(
                applicationContext,
                userDb::class.java,
                "User"
            ).build()

            withContext(Dispatchers.IO)
            {
                if (roomDb.userQuery().getUserId().isNullOrEmpty()) {
                    withContext(Dispatchers.Main){
                        getUserData()
                    }

                } else {
                    userData.userNumber = roomDb.userQuery().getUserNumber()
                    userData.userName = roomDb.userQuery().getUserId()
                    homeViewmodel.setUserData(userData)
                    homeViewmodel.getOffersHistory(userData.userNumber)
                }

            }
        }
//        lifecycleScope.launch {
//            getUserData()
//        }
        FirebaseApp.initializeApp(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.walletcardview.setBackgroundResource(R.drawable.walletbg)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setOnItemSelectedListener {
            if(it.itemId == R.id.home)
            {
                if(navController.currentDestination!!.id==R.id.monthlyOffers){
                    navController.navigate(R.id.action_monthlyOffers_to_weeklyOffers)
                }
                if(navController.currentDestination!!.id==R.id.redeem2){
                    navController.navigate(R.id.action_redeem2_to_weeklyOffers)
                }
                if(navController.currentDestination!!.id==R.id.profile){
                    navController.navigate(R.id.action_profile_fragment_to_weeklyOffers)
                }
            }
            if (it.itemId == R.id.history) {
                if (navController.currentDestination!!.id == R.id.weeklyOffers) {
                    navController.navigate(R.id.action_weeklyOffers_to_monthlyOffers)
                }
                if (navController.currentDestination!!.id == R.id.redeem2) {
                    navController.navigate(R.id.action_redeem2_to_monthlyOffers)
                }
                if(navController.currentDestination!!.id==R.id.profile){
                    navController.navigate(R.id.action_profile_fragment_to_monthlyOffers)
                }
            }
            if(it.itemId == R.id.redeem){
                if (navController.currentDestination!!.id == R.id.weeklyOffers) {
                    navController.navigate(R.id.action_weeklyOffers_to_redeem2)
                }
                if (navController.currentDestination!!.id == R.id.monthlyOffers) {
                    navController.navigate(R.id.action_monthlyOffers_to_redeem2)
                }
                if(navController.currentDestination!!.id==R.id.profile){
                    navController.navigate(R.id.action_profile_fragment_to_redeem2)
                }
            }
            if(it.itemId == R.id.profile_fragment){
                if (navController.currentDestination!!.id == R.id.weeklyOffers) {
                    navController.navigate(R.id.action_weeklyOffers_to_profile_fragment)
                }
                if (navController.currentDestination!!.id == R.id.monthlyOffers) {
                    navController.navigate(R.id.action_monthlyOffers_to_profile_fragment)
                }
                if (navController.currentDestination!!.id == R.id.redeem2) {
                    navController.navigate(R.id.action_redeem2_to_profile_fragment)
                }
            }
            true
        }




    }



    private fun getUserData() {
        homeViewmodel.getUserData(this)
        homeViewmodel.getuserData().observe(this, Observer { data ->
            userData = data!!
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    roomDb.userQuery().addUser(user = User(userData.userNumber , userData.userRecordId))
                }
            }
            homeViewmodel.withdrawalTransaction(data.userNumber)
            homeViewmodel.getOffersHistory(data.userRecordId)
            homeViewmodel.getWallet(userData.userRecordId)
        })

        homeViewmodel.getWalletPrice().observe(this, Observer { wallet ->
            binding.walletTextView.text = wallet.currentBal.toString()
        })
    }
    @Deprecated("Deprecated in Java", ReplaceWith("this.finish()"))
    override fun onBackPressed() {
       this.finish()
    }
}
