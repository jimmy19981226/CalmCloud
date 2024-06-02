package com.example.calmcloud.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calmcloud.MainActivity
import com.example.calmcloud.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            // Validate user credentials
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Perform login and navigate to the main activity
                loginUser(email, password)
            }
        }

        val tvRegister: TextView = findViewById(R.id.tvRegister)
        tvRegister.setOnClickListener {
            // Navigate to the registration activity
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        // Perform login logic here and navigate to MainActivity if successful
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

