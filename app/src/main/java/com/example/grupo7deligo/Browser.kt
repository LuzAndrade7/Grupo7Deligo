package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Browser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser) // Aquí se carga el layout de activity_browser.xml

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Botón Home
        val homeButton = findViewById<LinearLayout>(R.id.ll_nav_home)  // ID del LinearLayout de "Home"
        homeButton.setOnClickListener {
            val intent = Intent(this, Home::class.java) // Cambia a la pantalla Home
            startActivity(intent)
            finish()  // Para evitar que el usuario vuelva a Browser al presionar atrás
        }

        // Botón Explorar
        val exploreButton = findViewById<LinearLayout>(R.id.ll_nav_explore) // ID del LinearLayout de "Explorar"
        exploreButton.setOnClickListener {
            val intent = Intent(this, Browser::class.java) // Mantiene la pantalla de "Explorar" activa
            startActivity(intent)
            finish()  // Para evitar que el usuario regrese a la misma pantalla
        }

        // Botón Cuenta
        val accountButton = findViewById<LinearLayout>(R.id.ll_nav_account)  // ID del LinearLayout de "Cuenta"
        accountButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java) // Navega a la pantalla "Profile"
            startActivity(intent)
            finish()  // Para evitar que el usuario regrese a la pantalla Browser
        }

        // Configuración del botón de regreso en la parte superior izquierda
        val backButton = findViewById<ImageView>(R.id.btn_back)
        backButton.setOnClickListener {
            // Al presionar la flecha de regreso, navegar a la pantalla Home
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()  // Para evitar que el usuario regrese a Browser al presionar atrás
        }
    }
}
