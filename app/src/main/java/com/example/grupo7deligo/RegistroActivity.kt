package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore
class RegistroActivity : AppCompatActivity() {

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonContinue: MaterialButton
    private lateinit var backButton: ImageView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance()

        // Inicializar las vistas
        initViews()

        // Obtener los datos de PhoneNumberActivity
        val firstName = intent.getStringExtra("FIRST_NAME")
        val lastName = intent.getStringExtra("LAST_NAME")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")

        // Aquí puedes prellenar los campos con los datos recibidos si es necesario
        editTextFirstName.setText(firstName)
        editTextLastName.setText(lastName)

        setupClickListeners(firstName, lastName, email, phone)
    }

    private fun initViews() {
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonContinue = findViewById(R.id.buttonContinue)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupClickListeners(firstName: String?, lastName: String?, email: String?, phone: String?) {
        // Botón continuar
        buttonContinue.setOnClickListener {
            if (validateForm()) {
                // Si la validación es exitosa, guardar los datos y navegar al inicio
                saveUserDataToFirestore(firstName, lastName, email, phone)
            }
        }

        // Botón de regreso
        backButton.setOnClickListener {
            finish() // Cerrar esta actividad y regresar
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
                Toast.makeText(this, "Formulario válido", Toast.LENGTH_SHORT).show()
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
        return name.length >= 2 && name.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() }
    }

    private fun saveUserDataToFirestore(firstName: String?, lastName: String?, email: String?, phone: String?) {
        // Recoger los datos del formulario
        val firstName = firstName ?: ""
        val lastName = lastName ?: ""
        val email = email ?: ""
        val password = editTextPassword.text.toString().trim()

        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phone" to phone,
            "password" to password // Este es un ejemplo, nunca deberías guardar contraseñas en texto plano en Firestore
        )

        firestore.collection("users").add(userData)
            .addOnSuccessListener {
                // Datos guardados correctamente
                Toast.makeText(this, "Usuario guardado en Firestore", Toast.LENGTH_SHORT).show()
                // Redirigir a Home o a la siguiente actividad
                navigateToHome()
            }
            .addOnFailureListener { e ->
                // Error al guardar los datos
                Toast.makeText(this, "Error al guardar el usuario: $e", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish() // Opcional: para que no pueda regresar a la pantalla de registro
    }
}
