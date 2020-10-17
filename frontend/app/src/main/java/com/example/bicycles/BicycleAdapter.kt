package com.example.bicycles

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycles.models.Bicycle
import com.squareup.picasso.Picasso
import java.net.URL


class BicycleAdapter(var bicycleList: ArrayList<Bicycle>, val context: Context) : RecyclerView.Adapter<BicycleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bicycle_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(bicycleList[position], context)
    }

    override fun getItemCount(): Int {
        Log.v("hola caraoola", "kk")
        Log.v("hola caracola", bicycleList.size.toString())
        return bicycleList.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(b: Bicycle, context: Context){
            val url = "http://192.168.0.164:8080/img/bicycle-"
            val txt_brand: TextView = itemView.findViewById(R.id.textViewBrand)
            val txt_model: TextView = itemView.findViewById(R.id.textViewModel)
            val img: ImageView = itemView.findViewById(R.id.imageViewBicycle)

            txt_brand.text = b.brand
            txt_model.text = b.model

            val imageUrl = url + b.id.toString() + ".jpg"
            Picasso.with(context).load(imageUrl).into(img);
        }
    }
}