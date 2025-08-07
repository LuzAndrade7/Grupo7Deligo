

package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp

class PhoneNumberActivity : AppCompatActivity() {

    private lateinit var editTextPhone: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonContinue: MaterialButton
    private lateinit var buttonGoogleSignIn: MaterialButton
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)

        // Inicializar Firebase si no está ya inicializado
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance()

        // Inicializar las vistas
        initViews()
    }

    private fun initViews() {
        // Asignar las vistas a las variables
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextEmail = findViewById(R.id.editTextMail)
        buttonContinue = findViewById(R.id.buttonContinue)
        buttonGoogleSignIn = findViewById(R.id.buttonGoogleSignIn)

        // Configurar el botón de continuar
        buttonContinue.setOnClickListener {
            val phoneNumber = editTextPhone.text.toString().trim()

            if (validatePhoneNumber(phoneNumber)) {
                // Obtener los datos del usuario desde los campos de texto
                val firstName = editTextFirstName.text.toString().trim()
                val lastName = editTextLastName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()

                // Guardar los datos en Firestore
                saveUserToFirestore(firstName, lastName, email, phoneNumber)
            } else {
                // Mostrar error si el teléfono no tiene 10 dígitos
                Toast.makeText(this, "El número debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón de Google
        buttonGoogleSignIn.setOnClickListener {
            // Redirigir a la actividad de GoogleRegistro
            val intent = Intent(this, GoogleRegistroActivity::class.java)
            startActivity(intent)
        }
    }

    // Validar número de teléfono (solo dígitos y 10 caracteres)
    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val cleanPhone = phoneNumber.replace(Regex("[^\\d]"), "")
        return cleanPhone.length == 10
    }

    private fun saveUserToFirestore(firstName: String, lastName: String, email: String, phoneNumber: String) {
        // Crear un mapa con los datos del usuario
        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phone" to phoneNumber
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
        finish()  // Para que no pueda regresar a esta pantalla con el botón atrás
    }
}