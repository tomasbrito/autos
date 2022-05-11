package com.example.autos.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.autos.R


class PostFragment : Fragment() {


    lateinit var fragmentView : View
    lateinit var brand : TextView
    lateinit var model : TextView
    lateinit var detail : TextView
    lateinit var year : TextView
    lateinit var km : TextView
    lateinit var transmition : TextView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_post, container, false)
        brand = fragmentView.findViewById(R.id.txt_marca)
        model = fragmentView.findViewById(R.id.txt_modelo)
        detail = fragmentView.findViewById(R.id.txt_detalle)
        year = fragmentView.findViewById(R.id.txt_añoKmTrans)

        return fragmentView
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val post = PostFragmentArgs.fromBundle(requireArguments()).selectedPost

        brand.text = post.marca
        model.text = post.modelo
        detail.text = post.detalle
        year.text = "${post.año.toString()} • 9000 • Automatico "
    }


}