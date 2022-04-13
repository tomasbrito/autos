package com.example.autos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Hace funcionar el back button del ActionBar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.listaFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}