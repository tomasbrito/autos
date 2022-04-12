package com.example.autos.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autos.R
import com.example.autos.models.Publicacion
import com.example.autos.adapters.PublicacionesAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.*


class ListaFragment : Fragment() {

    private lateinit var publicacionesRecyclerView: RecyclerView
    var publicaciones = ArrayList<Publicacion>()
    private lateinit var fragmentView: View
    private lateinit var btn_filtros : Button
    var postsList = ArrayList<Publicacion>()
    private lateinit var lista : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        fragmentView = inflater.inflate(R.layout.fragment_lista,container,false)
        publicacionesRecyclerView = fragmentView.findViewById(R.id.recycler_publicaciones)
        btn_filtros = fragmentView.findViewById(R.id.button)
        getPostFromFirebase()

        configureRecyclerView()
        return fragmentView
    }

    private fun configureRecyclerView(){
        publicacionesRecyclerView.setHasFixedSize(true)
        publicacionesRecyclerView.layoutManager = LinearLayoutManager(context)
        publicacionesRecyclerView.adapter = PublicacionesAdapter(postsList,requireContext())

    }

    override fun onStart() {
        super.onStart()
        getPostFromFirebase()



        btn_filtros.setOnClickListener {
            lista.text =" a " +  postsList.get(0).id
        }
    }


    fun getPostFromFirebase(){
        val query = FirebaseFirestore.getInstance().collection("posts")
        Log.d(ContentValues.TAG, "getin 1")
        query.addSnapshotListener { snapshot, error ->
            Log.d(ContentValues.TAG, "getin 2")
            if (error != null) {
                Log.d(ContentValues.TAG, "getin 3")
                Log.d(ContentValues.TAG, "Listen failed.", error)
                return@addSnapshotListener
            }
            postsList.clear()
            if (snapshot != null) {

                for (post in snapshot) {
                    postsList.add(post.toObject())

                    Log.d(ContentValues.TAG, "get in 4 " + postsList.toArray())
                }
                //todo
                // Se tiene que llamar despues de que la lista post obtenga todos los post de firebase, sino rompe
                configureRecyclerView()


            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }

        }
    }


    // bottomsheet arreglar y cambiar cosas viejas
    fun onFilterClick () {
        var addPetBottomSheet = LayoutInflater
            .from(requireContext().applicationContext)
            .inflate(R.layout.bottomsheet, fragmentView.findViewById(R.id.bottomSheetContainer))

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetTheme)
        // Permite que se muestre completo el BottomsSheetDialog sino no se muestra por completo
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        var seekBar = addPetBottomSheet.findViewById<SeekBar>(R.id.seekBar)
        var txt_precioMax = addPetBottomSheet.findViewById<TextView>(R.id.txt_precioMax)
        var btnConfirmar : Button = addPetBottomSheet.findViewById(R.id.btn_confirmar)
        var btnCancelar  : Button = addPetBottomSheet.findViewById(R.id.btn_cancelar)

        txt_precioMax.text = seekBar.progress.toString()

        btnConfirmar.setOnClickListener{
            bottomSheetDialog.dismiss()
        }

        btnCancelar.setOnClickListener{
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(addPetBottomSheet)
        bottomSheetDialog.show()

    }


}