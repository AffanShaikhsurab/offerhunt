package com.example.oncash.Component


import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oncash.DataType.recommendation

import com.example.oncash.R
import com.example.oncash.View.Info


class Recommendation_RecyclerViewAdapter(val recommendation_list: List<recommendation>) :RecyclerView.Adapter<Recommendation_RecyclerViewAdapter.myview_holder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myview_holder {
        val viewholder =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_recyerview, parent, false)
        return myview_holder(viewholder, listener)
    }

    override fun onBindViewHolder(holder: myview_holder, position: Int) {
        val list = recommendation_list[position]

        holder.image.setImageResource(R.drawable.placeholder)
        //Glide library
        Glide.with(holder.itemView.context)
            .load(list.Image)
            .placeholder(R.drawable.placeholder)
            .into(holder.image)
        holder.item_name.text = list.ItemName
        holder.resturent.text = list.Restaurant

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Info::class.java)
            intent.putExtra("name", list.ItemName)
            intent.putExtra("work", list.Restaurant)
            intent.putExtra("Image", list.Image)
            // Add more data as needed
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateData(newList: List<recommendation>) {
        var itemList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return recommendation_list.size
    }

    class myview_holder(itemView: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.offer_image)
        val item_name: TextView = itemView.findViewById(R.id.item_name)
        val resturent: TextView = itemView.findViewById(R.id.restaurant_name)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(itemView, adapterPosition)
            }
        }
    }

}