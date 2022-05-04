package com.example.autos.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
import kotlinx.android.synthetic.main.bottomsheet.*
import java.util.*


class ListaFragment : Fragment() {

    private lateinit var publicacionesRecyclerView: RecyclerView
    private lateinit var fragmentView: View
    var postsList = ArrayList<Publicacion>()
    private lateinit var lista : TextView
    private lateinit var spinner_marca_db : String
    private  var año_desde : Int = 0
    private  var año_hasta : Int = 2200



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
        publicacionesRecyclerView.adapter = PublicacionesAdapter(postsList, requireContext()) { selectedPost -> onPostClick(
            selectedPost
        ) }

    }

    override fun onStart() {
        super.onStart()
        getPostFromFirebase()

    }

    //Funcion que se envia al adapter para llamar cuando el usuario toca un post
    private fun onPostClick(selectedPost : Publicacion) {

        //Guarda el post en las clases autogeneradas del navgraph que permiten pasar argumentos entre fragments
        val action = ListaFragmentDirections.actionListaFragmentToPostFragment2(selectedPost)
        fragmentView.findNavController().navigate(action)
    }




    //todo traer los posts de firebase
    //todo por ahora trae todos, no tiene ni aplica filtros
    fun getPostFromFirebase(){
        val query = FirebaseFirestore.getInstance().collection("posts").orderBy("precio", Query.Direction.DESCENDING)
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
                    Log.d(TAG, "getin 5" + postsList)
                }
                //todo
                // Se tiene que llamar despues de que la lista post obtenga todos los post de firebase, sino rompe
                configureRecyclerView()


            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }

        }
    }



    //todo bottomsheet  arreglar
    fun onFilterClick () {
        var filtersBottomSheet = LayoutInflater
            .from(requireContext().applicationContext)
            .inflate(R.layout.bottomsheet, fragmentView.findViewById(R.id.bottomSheetContainer))

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetTheme)
        // Permite que se muestre completo el BottomsSheetDialog sino no se muestra por completo
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        var btnConfirmar : Button = filtersBottomSheet.findViewById(R.id.btn_confirmar)
        var btnCancelar  : Button = filtersBottomSheet.findViewById(R.id.btn_cancelar)
        var spinner_marca : Spinner = filtersBottomSheet.findViewById(R.id.spinner_marca)
        var txtAñoDesde : EditText = filtersBottomSheet.findViewById(R.id.editText_desde)
        var txtAñoHasta : EditText = filtersBottomSheet.findViewById(R.id.editText_hasta)

        btnConfirmar.setOnClickListener{
            spinner_marca_db = spinner_marca.selectedItem.toString()
            var añod = txtAñoDesde.text.toString()
            año_desde = Integer.parseInt(añod)
            var añoh = txtAñoHasta.text.toString()
            año_hasta = Integer.parseInt(añoh)
            bottomSheetDialog.dismiss()
            getPostFromFirebase()
        }

        btnCancelar.setOnClickListener{
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(filtersBottomSheet)
        bottomSheetDialog.show()

    }


    //todo traer los posts de firebase
    fun getPostFromFirebase2(){
        //val query = FirebaseFirestore.getInstance().collection("posts").whereEqualTo("marca",spinner_marca_db)
        val query = FirebaseFirestore.getInstance().collection("posts")
                .whereGreaterThanOrEqualTo("año",año_desde)
                .whereLessThanOrEqualTo("año",año_hasta)
                .whereEqualTo("marca",null)

        Log.d(ContentValues.TAG, "getin 1 " + año_desde + " " + año_hasta)
        query.addSnapshotListener { snapshot, error ->
            Log.d(ContentValues.TAG, "getin 2")
            if (error != null) {
                Log.d(ContentValues.TAG, " getin Listen failed.", error)
                return@addSnapshotListener
            }
            Log.d(ContentValues.TAG, "getin 4")
            postsList.clear()
            Log.d(ContentValues.TAG, "getin 4")
            if (snapshot != null) {
                for (post in snapshot) {
                    postsList.add(post.toObject())
                    Log.d(ContentValues.TAG, "getin 4 " + postsList.toArray())
                }
                //todo
                // Se tiene que llamar despues de que la lista post obtenga todos los post de firebase, sino rompe
                configureRecyclerView()


            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }

        }
    }


    //todo //relacionado a barra superior
    //se llama para inflar el boton a la barra superior
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.historias_fragment_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater!!)
    }

    //funcionalidad del boton de la barra superior //todo //filtros
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Boton para mostrar el BottomSheet
            R.id.menu_filter -> {
                //getPostFromFirebaseOrdered("precio")
                onFilterClick()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}