package com.example.deligo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {

    private lateinit var locationEditText: EditText
    private lateinit var changeLocationButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // Asume que tu primer XML se llama activity_home.xml

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Obtener referencia al EditText de la ubicación
        locationEditText = findViewById(R.id.editText_location)

        // Obtener referencia al botón para cambiar la ubicación (Google Maps)
        changeLocationButton = findViewById(R.id.ic_arrow_drop_down)

        // Configurar el click listener para cambiar la ubicación
        changeLocationButton.setOnClickListener {
            openGoogleMapsForLocation()
        }

        // Configurar el click listener para el banner de McDonald's
        val mcdonaldsCard = findViewById<LinearLayout>(R.id.mcdonalds_card)
        mcdonaldsCard.setOnClickListener {
            val intent = Intent(this, RestaurantHomePage::class.java)
            startActivity(intent)
        }

        // Configurar el click listener para el botón de "Ofertas"
        val offersButton = findViewById<LinearLayout>(R.id.category_icons)
        offersButton.setOnClickListener {
            val intent = Intent(this, Offers::class.java)
            startActivity(intent)
        }

        // Configurar el click listener para el botón de "Buscar"
        val searchButton = findViewById<LinearLayout>(R.id.search_bar)
        searchButton.setOnClickListener {
            val intent = Intent(this, Browser::class.java)
            startActivity(intent)
        }

        // Configurar el click listener para el botón de "Cuenta"
        val accountButton = findViewById<LinearLayout>(R.id.ll_nav_account)  // Usamos el id asignado a "Cuenta"
        accountButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)  // Navega a la pantalla "Profile"
            startActivity(intent)
        }

        // Configurar el click listener para el botón de "Explorar"
        val exploreButton = findViewById<LinearLayout>(R.id.ll_nav_explore) // Usando el id correctamente
        exploreButton.setOnClickListener {
            val intent = Intent(this, Browser::class.java)  // Navega a la pantalla "Browser"
            startActivity(intent)
        }
    }

    private fun openGoogleMapsForLocation() {
        // Abre Google Maps para elegir la ubicación
        val gmmIntentUri = Uri.parse("geo:0,0?q=location")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}

