package com.example.grupo7deligo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class PhoneNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)

        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        val buttonContinue = findViewById<Button>(R.id.buttonContinue)

        buttonContinue.setOnClickListener {
            val phoneNumber = editTextPhone.text.toString().trim()

            // Valida que tenga mínimo 10 dígitos
            if (phoneNumber.matches(Regex("^[0-9]{9,}$"))) {
                // Aquí iría la siguiente pantalla
                val intent = Intent(this, Profile::class.java) // Reemplaza con tu destino real
                intent.putExtra("phone", phoneNumber)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ingresa un número válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}