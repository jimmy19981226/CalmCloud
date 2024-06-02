package com.example.calmcloud.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calmcloud.R
import com.example.calmcloud.api.RetrofitClient
import com.example.calmcloud.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        emailInput = findViewById(R.id.etEmail)
        passwordInput = findViewById(R.id.etPassword)
        confirmPasswordInput = findViewById(R.id.etConfirmPassword)
        registerButton = findViewById(R.id.btnRegister)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    registerUser(email, password)
                } else {
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        val user = User(email, password)
        RetrofitClient.api.registerUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegistrationActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to the login activity
                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegistrationActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RegistrationActivity, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

