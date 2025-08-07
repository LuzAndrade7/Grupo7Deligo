package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegistroActivity : AppCompatActivity() {
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonContinue: MaterialButton
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonContinue = findViewById(R.id.buttonContinue)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupClickListeners() {
        // Botón continuar
        buttonContinue.setOnClickListener {
            if (validateForm()) {
                navigateToHome()  // Después de validar, se va a Home
            }
        }

        // Botón de regreso
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun validateForm(): Boolean {
        val firstName = editTextFirstName.text.toString().trim()
        val lastName = editTextLastName.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Limpiar errores visuales previos
        clearFieldErrors()

        // Verificar que los campos no estén vacíos antes de realizar las validaciones adicionales
        return when {
            firstName.isEmpty() -> {
                showFieldError(editTextFirstName, "El nombre es obligatorio")
                false
            }
            lastName.isEmpty() -> {
                showFieldError(editTextLastName, "El apellido es obligatorio")
                false
            }
            password.isEmpty() -> {
                showFieldError(editTextPassword, "La contraseña es obligatoria")
                false
            }
            !isValidName(firstName) -> {
                showFieldError(editTextFirstName, "El nombre debe tener al menos 2 caracteres y solo letras")
                false
            }
            !isValidName(lastName) -> {
                showFieldError(editTextLastName, "El apellido debe tener al menos 2 caracteres y solo letras")
                false
            }
            !isValidPassword(password) -> {
                showFieldError(editTextPassword, "La contraseña debe tener al menos 6 caracteres, incluir mayúsculas, minúsculas y números")
                false
            }
            else -> {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    private fun showFieldError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun clearFieldErrors() {
        editTextFirstName.error = null
        editTextLastName.error = null
        editTextPassword.error = null
    }

    private fun isValidName(name: String): Boolean {
        // Verificar que el nombre tenga al menos 2 caracteres y solo contenga letras y espacios
        return name.length >= 2 && name.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))
    }

    private fun isValidPassword(password: String): Boolean {
        // Verificar que la contraseña tenga al menos 6 caracteres, una mayúscula, una minúscula y un número
        return password.length >= 6 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() }
    }

    private fun navigateToHome() {
        val intent = Intent(this, Home::class.java)
        // Pasar datos opcionales a Home
        intent.putExtra("USER_NAME", "${editTextFirstName.text} ${editTextLastName.text}")
        startActivity(intent)
        finish() // Para que no pueda volver con el botón atrás
    }
}
