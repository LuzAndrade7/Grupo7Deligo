package com.example.deligo

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.deligo.R

class CreditCardPaymentActivity : AppCompatActivity() {

    private lateinit var etCardNumber: EditText
    private lateinit var etExpiryDate: EditText
    private lateinit var etCvv: EditText
    private lateinit var etCardholderName: EditText
    private lateinit var cbSaveCard: CheckBox
    private lateinit var btnPayNow: Button
    private lateinit var btnBack: ImageView  // Inicialización del btnBack
    private lateinit var tvTotalAmount: TextView

    private var loadingDialog: AlertDialog? = null
    private val totalAmount = "$7.01"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_payment)

        // Inicializar las vistas
        initViews()

        // Configurar listeners
        setupListeners()

        // Configurar TextWatchers para formatear los campos de tarjeta
        setupTextWatchers()

    }

    private fun initViews() {
        // Inicializar las vistas desde el XML
        etCardNumber = findViewById(R.id.et_card_number)
        etExpiryDate = findViewById(R.id.et_expiry_date)
        etCvv = findViewById(R.id.et_cvv)
        etCardholderName = findViewById(R.id.et_cardholder_name)
        cbSaveCard = findViewById(R.id.cb_save_card)
        btnPayNow = findViewById(R.id.btn_pay_now)
        tvTotalAmount = findViewById(R.id.tv_total_amount)

        // Establecer el monto total en el TextView
        tvTotalAmount.text = totalAmount
        btnPayNow.text = "Pagar Ahora - $totalAmount"
    }

    private fun setupListeners() {

        // Configurar el botón de pago
        btnPayNow.setOnClickListener {
            if (validateCardData()) {
                processPayment()  // Iniciar el procesamiento del pago
            }
        }
    }

    private fun setupTextWatchers() {
        // Formatear número de tarjeta con espacios
        etCardNumber.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val text = s.toString().replace(" ", "")
                val formatted = StringBuilder()

                for (i in text.indices) {
                    if (i > 0 && i % 4 == 0) {
                        formatted.append(" ")
                    }
                    formatted.append(text[i])
                }

                etCardNumber.setText(formatted.toString())
                etCardNumber.setSelection(formatted.length)
                isFormatting = false
            }
        })

        // Formatear fecha de vencimiento MM/AA
        etExpiryDate.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val text = s.toString().replace("/", "")
                val formatted = StringBuilder()

                for (i in text.indices) {
                    if (i == 2) {
                        formatted.append("/")
                    }
                    formatted.append(text[i])
                }

                etExpiryDate.setText(formatted.toString())
                etExpiryDate.setSelection(formatted.length)
                isFormatting = false
            }
        })
    }

    private fun validateCardData(): Boolean {
        val cardNumber = etCardNumber.text.toString().replace(" ", "")
        val expiryDate = etExpiryDate.text.toString()
        val cvv = etCvv.text.toString()
        val cardholderName = etCardholderName.text.toString().trim()

        // Validar número de tarjeta
        if (cardNumber.length < 16) {
            showError("Por favor ingresa un número de tarjeta válido")
            etCardNumber.requestFocus()
            return false
        }

        // Validar fecha de vencimiento
        if (expiryDate.length != 5 || !expiryDate.contains("/")) {
            showError("Por favor ingresa una fecha de vencimiento válida (MM/AA)")
            etExpiryDate.requestFocus()
            return false
        }

        val parts = expiryDate.split("/")
        val month = parts[0].toIntOrNull()
        val year = parts[1].toIntOrNull()

        if (month == null || year == null || month < 1 || month > 12) {
            showError("La fecha de vencimiento no es válida")
            etExpiryDate.requestFocus()
            return false
        }

        // Validar que la tarjeta no esté vencida
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) % 100
        val currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1

        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            showError("La tarjeta está vencida")
            etExpiryDate.requestFocus()
            return false
        }

        // Validar CVV
        if (cvv.length != 3) {
            showError("Por favor ingresa un CVV válido de 3 dígitos")
            etCvv.requestFocus()
            return false
        }

        // Validar nombre del titular
        if (cardholderName.isEmpty() || cardholderName.length < 2) {
            showError("Por favor ingresa el nombre del titular de la tarjeta")
            etCardholderName.requestFocus()
            return false
        }

        // Validar que el nombre contenga al menos un espacio (nombre y apellido)
        if (!cardholderName.contains(" ")) {
            showError("Por favor ingresa el nombre completo del titular")
            etCardholderName.requestFocus()
            return false
        }

        return true
    }

    private fun processPayment() {
        showLoadingDialog()

        // Simular procesamiento de pago (en una app real, aquí harías la llamada a la API de pago)
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoadingDialog()
            showPaymentSuccess()
        }, 3000)
    }

    private fun showPaymentSuccess() {
        val cardNumber = etCardNumber.text.toString()
        val maskedCardNumber = "**** **** **** " + cardNumber.replace(" ", "").takeLast(4)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Pago Exitoso!")
        builder.setMessage(
            "Tu pago ha sido procesado exitosamente.\n\n" +
                    "Tarjeta: $maskedCardNumber\n" +
                    "Monto: $totalAmount\n" +
                    "Fecha: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(java.util.Date())}\n\n" +
                    "Encebollado\n" +
                    "Tiempo estimado: 25-30 minutos\n\n" +
                    "Recibirás una notificación cuando tu pedido esté listo."
        )

        builder.setPositiveButton("Continuar") { dialog, _ ->
            dialog.dismiss()
            // Opcional: regresar a la pantalla principal o mostrar tracking del pedido
            setResult(RESULT_OK)
            finish()  // Termina la actividad y vuelve a la pantalla anterior
        }

        builder.setCancelable(false)
        builder.create().show()
    }

    private fun showLoadingDialog() {
        val progressBar = ProgressBar(this).apply {
            isIndeterminate = true
        }

        val textView = TextView(this).apply {
            text = "Procesando pago..."
            gravity = Gravity.CENTER
            setPadding(0, 30, 0, 0)
            textSize = 16f
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(50, 50, 50, 50)
            addView(progressBar)
            addView(textView)
        }

        loadingDialog = AlertDialog.Builder(this)
            .setView(layout)
            .setCancelable(false)
            .create()

        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoadingDialog()
    }

    // Método para obtener el tipo de tarjeta basado en el número
    private fun getCardType(cardNumber: String): String {
        val number = cardNumber.replace(" ", "")
        return when {
            number.startsWith("4") -> "Visa"
            number.startsWith("5") -> "MasterCard"
            number.startsWith("3") -> "American Express"
            else -> "Tarjeta"
        }
    }
}
