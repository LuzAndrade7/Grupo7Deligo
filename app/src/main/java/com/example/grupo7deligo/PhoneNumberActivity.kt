package com.example.grupo7deligo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.button.MaterialButton


class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var editTextPhone: EditText
    private lateinit var buttonContinue: MaterialButton
    private lateinit var appleButton: LinearLayout
    private lateinit var googleButton: LinearLayout
    private lateinit var emailButton: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)
        initViews()
        setupClickListeners()
    }

        private fun initViews() {
            editTextPhone = findViewById(R.id.editTextPhone)
            buttonContinue = findViewById(R.id.buttonContinue)

            // Los botones sociales son LinearLayouts en tu diseño
            val socialButtonsContainer = findViewById<LinearLayout>(R.id.socialButtonsContainer)
            appleButton = socialButtonsContainer.getChildAt(0) as LinearLayout
            googleButton = socialButtonsContainer.getChildAt(1) as LinearLayout
            emailButton = socialButtonsContainer.getChildAt(2) as LinearLayout
        }

        private fun setupClickListeners() {
            // Continuar con teléfono
            buttonContinue.setOnClickListener {
                val phoneNumber = editTextPhone.text.toString().trim()

                if (validatePhoneNumber(phoneNumber)) {
                    // Si el teléfono es válido, ir a Home
                    navigateToHome()
                } else {
                    // Mostrar error si el teléfono no tiene 10 dígitos
                    Toast.makeText(this, "El número debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
                }
            }

            // Continuar con Apple
            appleButton.setOnClickListener {
                // Aquí puedes implementar la lógica de Apple Sign In
                Toast.makeText(this, "Inicio con Apple", Toast.LENGTH_SHORT).show()
                navigateToHome()
            }

            // Continuar con Google
            googleButton.setOnClickListener {
                // Aquí puedes implementar la lógica de Google Sign In
                Toast.makeText(this, "Inicio con Google", Toast.LENGTH_SHORT).show()
                navigateToHome()
            }

            // Continuar con Email
            emailButton.setOnClickListener {
                navigateToEmailLogin()
            }
        }

        private fun validatePhoneNumber(phoneNumber: String): Boolean {
            // Remover espacios y caracteres especiales, solo conservar dígitos
            val cleanPhone = phoneNumber.replace(Regex("[^\\d]"), "")
            return cleanPhone.length == 10
        }

        private fun navigateToHome() {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish() // Opcional: para que no pueda volver con el botón atrás
        }

        private fun navigateToEmailLogin() {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
}