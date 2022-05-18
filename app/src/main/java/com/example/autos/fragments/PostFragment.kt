package com.example.autos.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.autos.R
import kotlin.math.log


class PostFragment : Fragment() {


    lateinit var fragmentView : View
    lateinit var brand : TextView
    lateinit var model : TextView
    lateinit var detail : TextView
    lateinit var year : TextView
    lateinit var km : TextView
    lateinit var transmition : TextView
    lateinit var imageView: ImageView
    lateinit var btn_img_next : Button
    lateinit var btn_img_prev : Button
    var img_pos = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_post, container, false)
        brand = fragmentView.findViewById(R.id.txt_marca)
        model = fragmentView.findViewById(R.id.txt_modelo)
        detail = fragmentView.findViewById(R.id.txt_detalle)
        year = fragmentView.findViewById(R.id.txt_añoKmTrans)
        imageView = fragmentView.findViewById(R.id.imageView)
        btn_img_next = fragmentView.findViewById(R.id.btn_img_next)
        btn_img_prev = fragmentView.findViewById(R.id.btn_img_prev)

        return fragmentView
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        var post = PostFragmentArgs.fromBundle(requireArguments()).selectedPost

        brand.text = post.marca
        model.text = post.modelo
        detail.text = post.detalle
        year.text = "${post.año.toString()} • 9000 • Automatico "
        Glide.with(requireContext())
            .load(post.urlImg)
            .into(imageView)


        var listUrl = ArrayList<String>()

        listUrl.add(post.urlImg)
        listUrl.add("https://content.tinytap.it/Numero_1_y_2__db7ae6b0-96d4-11eb-aa1a-fb8914ed108a/coverImage813x610.jpg")
        listUrl.add("https://st2.depositphotos.com/1561359/12186/v/600/depositphotos_121863800-stock-illustration-3d-shiny-blue-number-2.jpg")
        listUrl.add("https://dibujosycolores.com/numeros/numero-3/numero-3-7.jpg")
        listUrl.add("https://images.kavak.services/images/154426/EXTERIOR-back-1640288174112.jpeg?d=756x434")

        btn_img_next.setOnClickListener{
            changeImage(1, listUrl)
        }

        btn_img_prev.setOnClickListener{
            changeImage(-1, listUrl)
        }

    }

    //todo no lo usa mas porque ahora da la vuelta en las fotos
    //habilita o deshabilita los botones para cambiar de foton segun en que foto este
    private fun setButtons(btn_prev: Button,btn_next: Button, listUrl : ArrayList<String>) {
        if (img_pos == 0){
            btn_prev.isEnabled = false
            btn_prev.isClickable = false
        } else {
            btn_prev.isEnabled = true
            btn_prev.isClickable = true
        }

        if (img_pos == listUrl.size - 1){
            btn_next.isEnabled = false
            btn_next.isClickable = false
        } else {
            btn_next.isEnabled = true
            btn_next.isClickable = true
        }
    }

    private fun changeImage(masmenos : Int, listUrl : ArrayList<String>) {
        img_pos += masmenos

        //si esta en la ultima foto pasa a la primera
        if(img_pos == listUrl.size){
            img_pos = 0
        } else if (img_pos == -1){ //si esta en la primera foto pasa a la ultima
            img_pos = (listUrl.size - 1 )
        }



        Glide.with(requireContext())
            .load(listUrl[img_pos])
            .into(imageView)



        Log.d(TAG, "position2 $listUrl")
        Log.d(TAG, "position3 $img_pos")
    }



}