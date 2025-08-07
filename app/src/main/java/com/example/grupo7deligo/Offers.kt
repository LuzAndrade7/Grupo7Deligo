package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Offers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_offers)

        // Configurar el diseño para los márgenes y el sistema de barras
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar los click listeners para los botones de navegación
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Botón Home
        val homeButton = findViewById<LinearLayout>(R.id.ll_nav_home)  // ID del LinearLayout de "Home"
        homeButton.setOnClickListener {
            val intent = Intent(this, Home::class.java) // Cambia a la pantalla Home
            startActivity(intent)
            finish()  // Para evitar que el usuario regrese a Offers
        }

        // Botón Explorar
        val exploreButton = findViewById<LinearLayout>(R.id.ll_nav_explore)  // ID del LinearLayout de "Explorar"
        exploreButton.setOnClickListener {
            val intent = Intent(this, Browser::class.java) // Cambia a la pantalla Browser
            startActivity(intent)
            finish()  // Para evitar que el usuario regrese a Offers
        }

        // Botón Cuenta
        val accountButton = findViewById<LinearLayout>(R.id.ll_nav_account)  // ID del LinearLayout de "Cuenta"
        accountButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java) // Cambia a la pantalla Profile
            startActivity(intent)
            finish()  // Para evitar que el usuario regrese a Offers
        }

        // Configuración del botón de regreso (si existe en la parte superior)
        val backButton = findViewById<ImageView>(R.id.btn_back)
        backButton.setOnClickListener {
            // Al presionar la flecha de regreso, navegar a la pantalla Home
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()  // Para evitar que el usuario regrese a Offers
        }
    }
}

