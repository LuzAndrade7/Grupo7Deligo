package com.example.deligo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class Bill : AppCompatActivity() {

    private var loadingDialog: AlertDialog? = null
    private lateinit var paymentLauncher: ActivityResultLauncher<Intent>  // Declarar el ActivityResultLauncher
    private val CREDIT_CARD_REQUEST_CODE = 100

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        // Inicializar el ActivityResultLauncher
        paymentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // El pago con tarjeta fue exitoso
                mostrarMensaje("¡Pago procesado exitosamente!")
            } else {
                // El usuario canceló el pago
                mostrarMensaje("Pago cancelado")
            }
        }

        val orderButton = findViewById<TextView>(R.id.order_button)
        orderButton.setOnClickListener {
            mostrarOpcionesPago()
        }

        val btnClose = findViewById<ImageView>(R.id.btn_close)
        btnClose.setOnClickListener {
            finish()
        }

        configurarOtrasOpciones()
    }

    private fun mostrarOpcionesPago() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Método de Pago")
        builder.setMessage("Selecciona tu método de pago preferido:")

        builder.setPositiveButton("Efectivo") { dialog, _ ->
            mostrarConfirmacionPago("efectivo")
            dialog.dismiss()
        }

        builder.setNeutralButton("Tarjeta de Crédito") { dialog, _ ->
            abrirPantallaTarjeta()  // Llamar al método para abrir la pantalla de pago con tarjeta
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun abrirPantallaTarjeta() {
        // Crear intent para abrir la pantalla de pago con tarjeta
        val intent = Intent(this, CreditCardPaymentActivity::class.java)

        // Iniciar la actividad utilizando el launcher
        paymentLauncher.launch(intent)
    }


    private fun mostrarConfirmacionPago(metodoPago: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar Pago")
        builder.setMessage("¿Estás seguro de que deseas proceder con el pago de $7.01 en $metodoPago?")

        builder.setPositiveButton("Confirmar") { dialog, _ ->
            procesarPago()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun procesarPago() {
        mostrarLoading()

        Handler(Looper.getMainLooper()).postDelayed({
            ocultarLoading()
            mostrarExitoPago()
        }, 2000)
    }

    private fun mostrarExitoPago() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Pago Exitoso!")
        builder.setMessage("Tu pedido ha sido confirmado.\n\nEncebollado\nTiempo estimado: 25-30 minutos")

        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.setCancelable(false)
        builder.create().show()
    }

    private fun mostrarLoading() {
        val progressBar = ProgressBar(this).apply {
            isIndeterminate = true
        }

        val texto = TextView(this).apply {
            text = "Procesando pago..."
            gravity = Gravity.CENTER
            setPadding(0, 30, 0, 0)
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(50, 50, 50, 50)
            addView(progressBar)
            addView(texto)
        }

        loadingDialog = AlertDialog.Builder(this)
            .setView(layout)
            .setCancelable(false)
            .create()

        loadingDialog?.show()
    }

    private fun ocultarLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun configurarOtrasOpciones() {
        // Configuraciones adicionales (si es necesario)
    }

    private fun mostrarMensaje(mensaje: String) {
        AlertDialog.Builder(this)
            .setMessage(mensaje)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        ocultarLoading()
    }
}
