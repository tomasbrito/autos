package com.example.autos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.autos.R
import com.example.autos.models.Publicacion
import java.text.DateFormat

class PublicacionesAdapter(private var postList: MutableList<Publicacion>, var context: Context): RecyclerView.Adapter<PublicacionesAdapter.PublicacionesHolder>() {


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

        Glide
            .with(context)
            .load(postList[position].urlImg)
            .into(holder.getImageView())

    }


    class PublicacionesHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view : View

        init {
            this.view = v
        }


        fun setMarca(txtMarca : String){
            val txt : TextView = view.findViewById(R.id.txt_marca)
            txt.text = txtMarca
        }

        fun setModelo(txtModelo : String){
            val txt : TextView = view.findViewById(R.id.txt_modelo)
            txt.text = txtModelo
        }

        fun setPrecio(txtPrecio : Int){
            val txt : TextView = view.findViewById(R.id.txt_precio)
            txt.text = "$" + txtPrecio.toString()
        }

        fun setDetalle(txtDetalle : String){
            val txt : TextView = view.findViewById(R.id.txt_detalle)
            txt.text = txtDetalle
        }

        fun getImageView () : ImageView {
            return view.findViewById(R.id.img_car)
        }

    }
}