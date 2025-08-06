package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegistroActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
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
        editTextEmail = findViewById(R.id.editTextEmail)
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
                navigateToHome()
            }
        }

        // Botón de regreso
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun validateForm(): Boolean {
        val email = editTextEmail.text.toString().trim()
        val firstName = editTextFirstName.text.toString().trim()
        val lastName = editTextLastName.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Limpiar errores visuales previos
        clearFieldErrors()

        when {
            email.isEmpty() -> {
                showFieldError(editTextEmail, "El correo electrónico es obligatorio")
                return false
            }
            !isValidEmail(email) -> {
                showFieldError(editTextEmail, "Ingresa un correo electrónico válido (ejemplo@correo.com)")
                return false
            }
            firstName.isEmpty() -> {
                showFieldError(editTextFirstName, "El nombre es obligatorio")
                return false
            }
            !isValidName(firstName) -> {
                showFieldError(editTextFirstName, "El nombre debe tener al menos 2 caracteres y solo letras")
                return false
            }
            lastName.isEmpty() -> {
                showFieldError(editTextLastName, "El apellido es obligatorio")
                return false
            }
            !isValidName(lastName) -> {
                showFieldError(editTextLastName, "El apellido debe tener al menos 2 caracteres y solo letras")
                return false
            }
            password.isEmpty() -> {
                showFieldError(editTextPassword, "La contraseña es obligatoria")
                return false
            }
            !isValidPassword(password) -> {
                showFieldError(editTextPassword, "La contraseña debe tener al menos 6 caracteres, incluir mayúsculas, minúsculas y números")
                return false
            }
            else -> {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                return true
            }
        }
    }

    private fun showFieldError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Cambiar el borde del campo a rojo (opcional, si tienes drawable para error)
        // editText.setBackgroundResource(R.drawable.bg_input_error)
    }

    private fun clearFieldErrors() {
        editTextEmail.error = null
        editTextFirstName.error = null
        editTextLastName.error = null
        editTextPassword.error = null

        // Restaurar el borde normal (opcional)
        // editTextEmail.setBackgroundResource(R.drawable.bg_input_rectangle)
        // editTextFirstName.setBackgroundResource(R.drawable.bg_input_rectangle)
        // editTextLastName.setBackgroundResource(R.drawable.bg_input_rectangle)
        // editTextPassword.setBackgroundResource(R.drawable.bg_input_rectangle)
    }

    private fun isValidName(name: String): Boolean {
        // Verificar que tenga al menos 2 caracteres y solo contenga letras y espacios
        return name.length >= 2 && name.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))
    }

    private fun isValidPassword(password: String): Boolean {
        // Verificar que tenga al menos 6 caracteres, una mayúscula, una minúscula y un número
        return password.length >= 6 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun navigateToHome() {
        val intent = Intent(this, Home::class.java)
        // Pasar datos opcionales a Home
        intent.putExtra("USER_EMAIL", editTextEmail.text.toString())
        intent.putExtra("USER_NAME", "${editTextFirstName.text} ${editTextLastName.text}")
        startActivity(intent)
        finish() // Opcional: para que no pueda volver con el botón atrás
    }
}