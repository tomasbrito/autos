package com.example.autos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.autos.R


class PostFragment : Fragment() {


    lateinit var fragmentView : View
    lateinit var id_post : TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_post, container, false)
        id_post = fragmentView.findViewById(R.id.idPost)

        return fragmentView
    }

    override fun onStart() {
        super.onStart()
        val post = PostFragmentArgs.fromBundle(requireArguments()).selectedPost
        id_post.text = post.id

    }


}