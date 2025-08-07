package com.example.deligo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.material.button.MaterialButton

class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var editTextPhone: EditText
    private lateinit var buttonContinue: MaterialButton
    private lateinit var googleButton: LinearLayout
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)
        initViews()
        setupGoogleSignIn()
    }

    private fun initViews() {
        editTextPhone = findViewById(R.id.editTextPhone)
        buttonContinue = findViewById(R.id.buttonContinue)

        // Los botones sociales son LinearLayouts en tu diseño
        val socialButtonsContainer = findViewById<LinearLayout>(R.id.socialButtonsContainer)
        googleButton = socialButtonsContainer.getChildAt(0) as LinearLayout  // Asegúrate de que este sea el botón de Google
    }

    private fun setupGoogleSignIn() {
        // Configura Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))  // Usamos el ID de cliente que agregamos en strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        // Manejo del clic en el botón de Google
        googleButton.setOnClickListener {
            signInWithGoogle()
        }

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
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)  // Usamos 9001 como código de solicitud
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Error en el inicio de sesión con Google
                Toast.makeText(this, "Error en el inicio de sesión con Google", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso, redirigir a Home
                    navigateToHome()
                } else {
                    // Error en el inicio de sesión
                    Toast.makeText(this, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
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
}
