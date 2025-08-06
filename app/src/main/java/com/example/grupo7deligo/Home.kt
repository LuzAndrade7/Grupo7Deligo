package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // Asume que tu primer XML se llama activity_home.xml

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Obtener referencia al banner de McDonald's
        val mcdonaldsCard = findViewById<LinearLayout>(R.id.mcdonalds_card)

        // Configurar el click listener
        mcdonaldsCard.setOnClickListener {
            // Crear intent para navegar a la pantalla de detalles del restaurante
            val intent = Intent(this, RestaurantHomePage::class.java)

            // Opcional: pasar datos adicionales si es necesario
            intent.putExtra("restaurant_name", "McDonald's")
            intent.putExtra("restaurant_rating", "4.9")
            intent.putExtra("delivery_time", "25 - 35 min")

            // Iniciar la nueva actividad
            startActivity(intent)
        }

        // Opcional: Agregar animación de transición
        // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}