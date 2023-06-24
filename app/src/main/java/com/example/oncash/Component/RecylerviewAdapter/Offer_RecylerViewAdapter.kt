package com.example.oncash.Component.RecylerviewAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import com.example.oncash.R
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oncash.DataType.Places
import com.example.oncash.View.Info


class Offer_RecylerViewAdapter() : RecyclerView.Adapter<Offer_RecylerViewAdapter.viewholder>() {
    lateinit var offerList : ArrayList<Places>
    var lastPosition = -1

    var context : Context?=null
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView
        val address :TextView
        val image : ImageView
        lateinit var  offerId :String
        init {
            name = itemView.findViewById(com.example.oncash.R.id.place_name)
            address = itemView.findViewById(com.example.oncash.R.id.place_address)
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
        holder.address.text = offerList[position].Address
        if (offerList[position].Banner!!.isNotEmpty()){
            Glide.with(holder.itemView.context).load(offerList[position].Banner).into(holder.image)
        }

        holder.itemView.setOnClickListener {
            val offer_information : Places = offerList.get(holder.offerId.toInt()-1)
            val intent = Intent(
                holder.itemView.context,
                Info::class.java
            )

                .putExtra("PlaceName",offer_information.Name)
                .putExtra("PlaceBanner",offer_information.Banner)
                .putExtra("PlaceAddress",offer_information.Address)
                .putExtra("PlaceId",offer_information.PlacesId)
                .putExtra("Category",offer_information.Category)


            holder.itemView.context.startActivity(
                intent
            )
        }

    }

    override fun getItemCount(): Int {

        return offerList. size

    }

    override fun onViewDetachedFromWindow(holder: viewholder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list :ArrayList<Places>){
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