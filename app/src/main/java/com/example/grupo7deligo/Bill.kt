package com.example.deligo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Bill : AppCompatActivity() {
    private var loadingDialog: AlertDialog? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        val orderButton = findViewById<TextView>(R.id.order_button)
        orderButton.setOnClickListener {
            mostrarConfirmacionPago()
        }

        val btnClose = findViewById<ImageView>(R.id.btn_close)
        btnClose.setOnClickListener {
            finish()
        }

        configurarOtrasOpciones()
    }

    private fun mostrarConfirmacionPago() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar Pago")
        builder.setMessage("¿Estás seguro de que deseas proceder con el pago de $7.01?")

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
        builder.setMessage("Tu pedido ha sido confirmado.\n\nCajita Feliz Hamburguesa\nTiempo estimado: 25-30 minutos")

        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
            // finish() // Descomenta si quieres cerrar la pantalla después del pago
        }

        builder.setCancelable(false)
        builder.create().show()
    }

    private fun mostrarLoading() {
        val progressBar = ProgressBar(this).apply {
            isIndeterminate = true
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(50, 50, 50, 50)
            addView(progressBar)
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
