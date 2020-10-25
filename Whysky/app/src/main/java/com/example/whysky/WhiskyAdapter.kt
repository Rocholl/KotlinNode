package com.example.whysky

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.whysky.models.Whisky

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import com.squareup.picasso.Picasso
class WhiskyAdapter(var whiskyList: ArrayList<Whisky>, val context: Context) : RecyclerView.Adapter<WhiskyAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.whisky_list_row, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(whiskyList[position], context)
    }

    override fun getItemCount(): Int {
        Log.v("hola caraoola", "kk")
        Log.v("hola caracola", whiskyList.size.toString())
        return whiskyList.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(w: Whisky, context: Context){

            val txt_nombre: TextView = itemView.findViewById(R.id.textViewNombre)
            val txt_descripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
            val img_url: ImageView = itemView.findViewById(R.id.imageViewImage)
            val txt_procedencia: TextView = itemView.findViewById(R.id.textViewProcedencia)




            txt_nombre.text = w.nombre

            txt_descripcion.text = w.descripcion
            txt_procedencia.text = w.procedencia
            Picasso.get()
                .load(w.image)
                .into(img_url)



        }
    }
}