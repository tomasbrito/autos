package com.example.autos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //La barra de navegcion inferior
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        //El fragmento principal que es el punto de entrada a los demas fragmentos
        val navController = findNavController(R.id.mainFragment)
        //Hace funcionar la navegacion de la barra inferior (BottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

    }

    // Hace funcionar el back button del ActionBar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.listaFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}