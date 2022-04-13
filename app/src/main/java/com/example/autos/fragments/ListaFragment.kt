package com.example.autos.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autos.R
import com.example.autos.adapters.PublicacionesAdapter
import com.example.autos.models.Publicacion
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.util.*


class ListaFragment : Fragment() {

    private lateinit var publicacionesRecyclerView: RecyclerView
    var publicaciones = ArrayList<Publicacion>()
    private lateinit var fragmentView: View
    var postsList = ArrayList<Publicacion>()
    private lateinit var lista : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        fragmentView = inflater.inflate(R.layout.fragment_lista, container, false)
        publicacionesRecyclerView = fragmentView.findViewById(R.id.recycler_publicaciones)
        getPostFromFirebase()

        configureRecyclerView()
        return fragmentView
    }

    private fun configureRecyclerView(){
        publicacionesRecyclerView.setHasFixedSize(true)
        publicacionesRecyclerView.layoutManager = LinearLayoutManager(context)
        publicacionesRecyclerView.adapter = PublicacionesAdapter(postsList, requireContext())

    }

    override fun onStart() {
        super.onStart()
        getPostFromFirebase()

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

    //todo ordenado por el parametro que se pase solo en forma desc
    fun getPostFromFirebaseOrdered(orderBy: String){
        val query = FirebaseFirestore.getInstance().collection("posts").orderBy(orderBy, Query.Direction.DESCENDING)

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

    //todo relacionado a barra superior
    //se llama para inflar el boton a la barra superior
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.historias_fragment_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater!!)
    }

    //funcionalidad del boton de la barra superior //todo filtros
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Boton para mostrar el BottomSheet
            R.id.menu_filter -> {
                getPostFromFirebaseOrdered("precio")
            }
        }
        return super.onOptionsItemSelected(item)
    }

}