package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {

    private lateinit var tvUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Iniciar el TextView donde se mostrará el nombre del usuario
        tvUserName = findViewById(R.id.tv_user_name)

        // Obtener el nombre del usuario del Intent
        val userName = intent.getStringExtra("USER_NAME")

        // Mostrar el nombre en el TextView
        tvUserName.text = userName ?: "Usuario no encontrado"

        // Configurar los click listeners para los botones de navegación
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Botón Home
        val homeButton = findViewById<LinearLayout>(R.id.ll_nav_home)  // ID del LinearLayout de "Home"
        homeButton.setOnClickListener {
            val intent = Intent(this, Home::class.java) // Cambia a la pantalla Home
            startActivity(intent)
        }

        // Botón Explorar
        val exploreButton = findViewById<LinearLayout>(R.id.ll_nav_explore)  // ID del LinearLayout de "Explorar"
        exploreButton.setOnClickListener {
            val intent = Intent(this, Browser::class.java) // Cambia a la pantalla Browser
            startActivity(intent)
        }

        // Botón Cuenta (ya estás en la pantalla de "Profile", no necesitas hacer nada aquí)
        val accountButton = findViewById<LinearLayout>(R.id.ll_nav_account)  // ID del LinearLayout de "Cuenta"
        accountButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java) // Se mantiene en la pantalla Profile
            startActivity(intent)
        }
    }
}

