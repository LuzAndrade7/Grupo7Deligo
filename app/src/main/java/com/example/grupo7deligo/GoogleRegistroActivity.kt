package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.button.MaterialButton

class GoogleRegistroActivity : AppCompatActivity() {

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonContinue: MaterialButton
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_registro)

        firestore = FirebaseFirestore.getInstance()

        // Inicializar las vistas
        initViews()

        // Obtener los datos pasados desde PhoneNumberActivity
        val firstName = intent.getStringExtra("FIRST_NAME")
        val email = intent.getStringExtra("EMAIL")

        // Prellenar los campos con los datos de Google
        editTextFirstName.setText(firstName)
        editTextEmail.setText(email)
    }

    private fun initViews() {
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextEmail = findViewById(R.id.editTextMail)
        buttonContinue = findViewById(R.id.buttonContinue)

        buttonContinue.setOnClickListener {
            val firstName = editTextFirstName.text.toString().trim()
            val lastName = editTextLastName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()

            // Validar si los campos están llenos
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
                saveUserToFirestore(firstName, lastName, email)
            } else {
                // Mostrar mensaje si hay campos vacíos
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserToFirestore(firstName: String, lastName: String, email: String) {
        // Crear un mapa con los datos del usuario
        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email
        )

        // Guardar los datos del usuario en Firestore
        firestore.collection("users").document(email)  // Usamos el correo como ID del documento
            .set(userData)
            .addOnSuccessListener {
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                navigateToHomeActivity()  // Navegar a la actividad principal
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al registrar el usuario: $e", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToHomeActivity() {
        // Navegar a la actividad principal después del registro
        val intent = Intent(this, Home::class.java)
            startActivity(intent)
        finish()
    }
}
