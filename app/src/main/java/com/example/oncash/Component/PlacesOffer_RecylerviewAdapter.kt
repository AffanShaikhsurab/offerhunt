package com.example.oncash.Component

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oncash.R


class PlacesOffer_RecylerviewAdapter : RecyclerView.Adapter<PlacesOffer_RecylerviewAdapter.viewholder>() {
    var ImageList : ArrayList<String> = ArrayList<String>()

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

        Glide.with(holder.itemView.context).load(ImageList[position]).into(holder.Image)

    }

    override fun getItemCount(): Int {

        return ImageList.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list :ArrayList<String>){
        this.ImageList.clear()
        this.ImageList.addAll(list)
        notifyDataSetChanged()
    }
}