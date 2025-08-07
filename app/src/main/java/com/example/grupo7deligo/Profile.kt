package com.example.deligo

import android.os.Bundle
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
    }
}
