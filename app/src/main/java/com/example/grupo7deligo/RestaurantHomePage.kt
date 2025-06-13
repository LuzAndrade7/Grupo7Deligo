package com.example.grupo7deligo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RestaurantHomePage : AppCompatActivity() {

    private lateinit var btnAddToCart: TextView
    private var itemsInCart = 0
    private var cartTotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_restaurant_home_page)

        // Configurar edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBackButton()
        setupDataFromIntent()
        initViews()
        setupClickListeners()
    }

    private fun setupBackButton() {
        // Configurar el botón de regreso
        val backButton = findViewById<ImageView>(R.id.btn_back)
        backButton.setOnClickListener {
            // Regresar a la pantalla anterior
            finish()
        }
    }

    private fun setupDataFromIntent() {
        // Obtener datos pasados desde la actividad anterior (opcional)
        val restaurantName = intent.getStringExtra("restaurant_name")


        // Usar los datos recibidos si es necesario
        restaurantName?.let {
            // Aquí podrías actualizar algún TextView si fuera necesario
            // Por ejemplo: findViewById<TextView>(R.id.restaurant_title).text = it
        }
    }

    private fun initViews() {
        btnAddToCart = findViewById(R.id.btn_add_to_cart)
    }

    private fun setupClickListeners() {
        btnAddToCart.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        itemsInCart++
        cartTotal += 5.99 // Precio del item

        Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()

        // Navegar a la pantalla del carrito
        val intent = Intent(this, FoodMenu::class.java)
        intent.putExtra("items_count", itemsInCart)
        intent.putExtra("cart_total", cartTotal)
        startActivity(intent)
    }
}