package com.example.oncash.Component

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import com.example.oncash.R
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.userData
import com.example.oncash.View.Info
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


class Offer_RecylerViewAdapter(val userData :userData) : RecyclerView.Adapter<Offer_RecylerViewAdapter.viewholder>() {
    var offerList : ArrayList<Offer> = ArrayList<Offer>()
    var lastPosition = -1

    var context : Context?=null
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView
        val address :TextView
        val min_offer :TextView
        val max_offer : TextView
        val image : ImageView
        lateinit var  offerId :String
        init {
            name = itemView.findViewById(com.example.oncash.R.id.places_name)
            address = itemView.findViewById(com.example.oncash.R.id.place_address)
            min_offer = itemView.findViewById(R.id.min_offer)
            max_offer = itemView.findViewById(R.id.offer_max_offer)
            image = itemView.findViewById(R.id.offer_imageview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        context = parent.context


        val v = LayoutInflater.from(parent.context).inflate(R.layout.offer_recyerview,parent,false)


        return viewholder(v)



    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.name.text= offerList[position].Name
        holder.max_offer.text = offerList[position].MaxDiscount
        holder.min_offer.text = offerList[position].MinDiscount
        holder.address.text = offerList[position].Address
        Glide.with(holder.itemView.context).load(offerList[position].Image).into(holder.image)

        val url :URL = URL( offerList[position].Image )

        var colour  :String = ""
        GlobalScope.launch { withContext(Dispatchers.IO) {
            val background = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            colour =  Integer.toHexString( getDominantColor(background)).substring(2)
//            withContext(Dispatchers.Main){
//                holder.background.background = linearGradientDrawable(colour)
//            }
        }
            }
//
//        val animation = AnimationUtils.loadAnimation(
//            context, if (position > lastPosition) {com.example.oncash.R.anim.offeranimation }else {com.example.oncash.R.anim.offeranimationdown}
//        )
//        holder.itemView.startAnimation(animation)
//        lastPosition = position

//            holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context , R.anim.offeranimation)


        holder.itemView.setOnClickListener {
            Toast.makeText( holder.itemView.context, userData.userRecordId , Toast.LENGTH_LONG).show()
            val offer_information : Offer = offerList.get(holder.offerId.toInt()-1)
            val intent = Intent(
                holder.itemView.context,
                Info::class.java
            )

                .putExtra("OfferName",offer_information.Name)
                .putExtra("OfferImage",offer_information.Image)
                .putExtra("OfferPrice",offer_information.Address)
                .putExtra("PLaceId",offer_information.Id)
                .putExtra("Category",offer_information.Category)


            holder.itemView.context.startActivity(
                intent

            )
        }

    }

    override fun getItemCount(): Int {

        return offerList.size

    }

    override fun onViewDetachedFromWindow(holder: viewholder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list :ArrayList<Offer>){
//        val result =  DiffUtil.calculateDiff( Diffutil(offerList , list))
        this.offerList.clear()
        this.offerList.addAll(list)
        notifyDataSetChanged()

//        result.dispatchUpdatesTo(this)
    }

    fun getDominantColor(bitmapp: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmapp!!, 10, 10, true)
        val color = newBitmap.getPixel(9, 9)
        newBitmap.recycle()
        return color
    }

    fun linearGradientDrawable(color: String): GradientDrawable {
        return GradientDrawable().apply {
            if (color.contains("fff"))
            {
                colors = intArrayOf(
                    Color.parseColor("#E6E3D3"),
                    Color.parseColor("#$color")

                )
            }else{
                colors = intArrayOf(
                    Color.parseColor("#$color"),
                    Color.parseColor("#ffffff")

                )
            }

            gradientType = GradientDrawable.LINEAR_GRADIENT
            shape = GradientDrawable.RECTANGLE
            orientation = GradientDrawable.Orientation.BL_TR

            // border around drawable
           // setStroke(5,Color.parseColor("#4B5320"))
        }
    }
}