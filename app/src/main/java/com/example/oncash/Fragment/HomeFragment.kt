package com.example.oncash.Fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oncash.Component.Offer_RecylerViewAdapter
import com.example.oncash.Component.Recommendation_RecyclerViewAdapter
import com.example.oncash.DataType.recommendation

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.oncash.R

class HomeFragment : Fragment() {
    private lateinit var recommendation_Recycler: RecyclerView
    private lateinit var dbref: DatabaseReference
    private val recommendationList: MutableList<recommendation> = mutableListOf()
    private lateinit var adapter: Recommendation_RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recommendation_Recycler = view.findViewById(R.id.recomendation_recycler)
        recommendation_Recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val adapter = Offer_RecylerViewAdapter(recommendationList)
        recommendation_Recycler.adapter = adapter

        getOfferData()

        return view
    }

    private fun getOfferData() {
        dbref = FirebaseDatabase.getInstance().getReference("Restaurant")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    recommendationList.clear()
                    for (restaurantSnapshot in snapshot.children) {
                        val recommendation = restaurantSnapshot.getValue(recommendation::class.java)
                        if (recommendation != null) {
                            recommendationList.add(recommendation)
                            Log.d("Firebase Data", recommendation.toString())
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", error.message)
            }
        })
    }
}

