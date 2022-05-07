package com.example.autos.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.autos.R
import com.google.android.material.slider.Slider


class PostFragment : Fragment() {


    lateinit var fragmentView : View
    lateinit var id_post : TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_post, container, false)
        id_post = fragmentView.findViewById(R.id.idPost)



        return fragmentView
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val post = PostFragmentArgs.fromBundle(requireArguments()).selectedPost
        id_post.text = "${post.id} ${post.año} ${post.marca} ${post.modelo} ${post.detalle}"




    }


}