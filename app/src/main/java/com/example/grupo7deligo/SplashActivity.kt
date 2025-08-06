package com.example.deligo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Redirige automáticamente a PhoneNumberActivity después de 2 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, PhoneNumberActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}