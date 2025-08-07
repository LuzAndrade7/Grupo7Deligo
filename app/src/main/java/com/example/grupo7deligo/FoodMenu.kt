package com.example.deligo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FoodMenu : AppCompatActivity() {

    private lateinit var btnViewCart: TextView
    private lateinit var btnBack: ImageView  // Declarar el botón de retroceso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Habilitar el borde completo (si es necesario para tu diseño)
        setContentView(R.layout.activity_food_menu)

        // Configurar edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()  // Inicializar las vistas
        setupClickListeners()  // Configurar los listeners de los botones
    }

    private fun initViews() {
        try {
            btnViewCart = findViewById(R.id.cart_button)  // Inicializar el botón del carrito
            btnBack = findViewById(R.id.btn_back)  // Inicializar el botón de retroceso

            Log.d("FoodMenu", "Botón encontrado correctamente")
        } catch (e: Exception) {
            Log.e("FoodMenu", "Error al encontrar el botón: ${e.message}")
            Toast.makeText(this, "Error: No se pudo encontrar el botón del carrito", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupClickListeners() {
        // Configurar el botón de ver carrito
        btnViewCart.setOnClickListener {
            val intent = Intent(this@FoodMenu, Bill::class.java)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No se pudo encontrar la actividad Bill", Toast.LENGTH_LONG).show()
            }
        }

        // Configurar el botón de retroceso
        btnBack.setOnClickListener {
            onBackPressed()  // Esto regresará a la pantalla anterior
        }
    }
}
