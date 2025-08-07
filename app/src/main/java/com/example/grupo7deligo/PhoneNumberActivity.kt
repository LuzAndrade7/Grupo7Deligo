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
        editTextEmail = findViewById(R.id.editTextMail)
        buttonContinue = findViewById(R.id.buttonContinue)
        buttonGoogleSignIn = findViewById(R.id.buttonGoogleSignIn)

        // Configurar el botón de continuar
        buttonContinue.setOnClickListener {
            val phoneNumber = editTextPhone.text.toString().trim()

            if (validatePhoneNumber(phoneNumber)) {
                // Obtener el correo desde el campo de texto
                val email = editTextEmail.text.toString().trim()

                // Guardar los datos en Firestore
                saveUserToFirestore(phoneNumber, email)
            } else {
                // Mostrar error si el teléfono no tiene 10 dígitos
                Toast.makeText(this, "El número debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuración para el botón de Google
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

    // Función para guardar el número de teléfono y correo en Firestore
    private fun saveUserToFirestore(phoneNumber: String, email: String) {
        // Crear un mapa con los datos del usuario
        val userData = hashMapOf(
            "phone" to phoneNumber,
            "email" to email
        )

        // Guardar los datos en Firestore
        firestore.collection("users").document(email)  // Usamos el correo como ID del documento
            .set(userData)
            .addOnSuccessListener {
                // Datos guardados correctamente
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()

                // Redirigir a RegistroActivity y pasar los datos necesarios
                val intent = Intent(this, RegistroActivity::class.java).apply {
                    putExtra("EMAIL", email)
                    putExtra("PHONE", phoneNumber)
                }
                startActivity(intent)
                finish()  // Cierra esta actividad
            }
            .addOnFailureListener { e ->
                // Error al guardar los datos
                Toast.makeText(this, "Error al registrar el usuario: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
