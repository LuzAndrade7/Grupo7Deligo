package com.example.deligo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Redirige a PhoneNumberActivity después de 3 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, PhoneNumberActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3 segundos
    }
}

