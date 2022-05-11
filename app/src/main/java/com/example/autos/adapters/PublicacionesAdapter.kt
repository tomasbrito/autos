package com.example.autos.adapters

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.autos.R
import com.example.autos.models.Publicacion

class PublicacionesAdapter(private var postList: MutableList<Publicacion>, var context: Context, var onPostClick: (Publicacion) -> Unit): RecyclerView.Adapter<PublicacionesAdapter.PublicacionesHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicacionesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_publicacion, parent, false)
        return (PublicacionesHolder(view))
    }

    override fun getItemCount(): Int {
        return postList.size
    }


    override fun onBindViewHolder(holder: PublicacionesHolder, position: Int) {
        holder.setMarca(postList[position].marca)
        holder.setDetalle(postList[position].detalle)
        holder.setPrecio(postList[position].precio)
        holder.setModelo(postList[position].modelo)
        holder.setAño(postList[position].año)

        Glide
            .with(context)
            .load(postList[position].urlImg)
            .into(holder.getImageView())

        holder.getCardLayout().setOnClickListener{
            onPostClick(postList[position])
        }

    }


    class PublicacionesHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view : View

        init {
            this.view = v
        }


        fun setMarca(txtMarca: String){
            val txt : TextView = view.findViewById(R.id.txt_marca)
            txt.text = txtMarca
        }

        fun setModelo(txtModelo: String){
            val txt : TextView = view.findViewById(R.id.txt_modelo)
            txt.text = txtModelo
        }

        fun setPrecio(txtPrecio: Int){
            val txt : TextView = view.findViewById(R.id.txt_precio)
            val formatter = DecimalFormat("#,###")
            var precio : String = "$" + (formatter.format(txtPrecio)).toString()
            precio = precio.replace(",",".")
            txt.text = precio

        }

        fun setDetalle(txtDetalle: String){
            val txt : TextView = view.findViewById(R.id.txt_detalle)
            txt.text = txtDetalle
        }

        fun getImageView () : ImageView {
            return view.findViewById(R.id.img_car)
        }

        fun setAño(txt_año: Int) {
            val txt : TextView = view.findViewById(R.id.txt_añoKmTrans)
            txt.text = txt_año.toString()
        }

        fun getCardLayout(): CardView {
            return view.findViewById(R.id.cardView)
        }


    }
}