package com.example.oncash.Component

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oncash.DataType.Offer
import com.example.oncash.R
import com.example.oncash.View.OfferInfoActivity
import com.example.oncash.ViewModel.offerInfo_viewModel


class PlacesOffer_RecylerviewAdapter(val viewModel : offerInfo_viewModel) : RecyclerView.Adapter<PlacesOffer_RecylerviewAdapter.viewholder>() {
    var OfferList : ArrayList<Offer> = ArrayList<Offer>()

    var context : Context?=null
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Image : ImageView

        init {
            Image = itemView.findViewById(R.id.place_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        context = parent.context


        val v = LayoutInflater.from(parent.context).inflate(R.layout.offer_steps_listview,parent,false)


        return viewholder(v)



    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewholder, position: Int) {

        Glide.with(holder.itemView.context).load(OfferList[position].OfferImage).into(holder.Image)
        holder.itemView.setOnClickListener{
            val offer :Offer = OfferList[position]
            viewModel.offer_info.value = offer
            val intent = Intent(context , OfferInfoActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return OfferList.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list :ArrayList<Offer>){
        this.OfferList.clear()
        this.OfferList.addAll(list)
        notifyDataSetChanged()
    }
}