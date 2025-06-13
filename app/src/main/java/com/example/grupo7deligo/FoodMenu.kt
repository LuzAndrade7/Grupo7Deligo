package com.example.grupo7deligo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FoodMenu : AppCompatActivity() {
    private lateinit var btnViewCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_food_menu)

        // Configurar edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupClickListeners()
    }
    @SuppressLint("QueryPermissionsNeeded")
    private fun setupClickListeners() {
        btnViewCart.setOnClickListener { view ->
            // Alternativa más explícita
            val intent = Intent()
            intent.setClass(this@FoodMenu, Bill::class.java)

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No se pudo encontrar la actividad Bill", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initViews() {
        try {
            btnViewCart = findViewById(R.id.cart_button)
            Log.d("FoodMenu", "Botón encontrado correctamente")
        } catch (e: Exception) {
            Log.e("FoodMenu", "Error al encontrar el botón: ${e.message}")
            Toast.makeText(this, "Error: No se pudo encontrar el botón del carrito", Toast.LENGTH_LONG).show()
        }
    }


}