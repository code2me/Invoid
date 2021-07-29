package com.example.invoid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        val userEmail = findViewById<TextView>(R.id.forget_email)
        val submitButton = findViewById<TextView>(R.id.submit_button)
        val backToLogin = findViewById<TextView>(R.id.back)

        backToLogin.setOnClickListener{
            startActivity(Intent(this@ForgetPasswordActivity, LoginActivity::class.java))
        }

        submitButton.setOnClickListener{
            val email: String = userEmail.text.toString().trim{ it <= ' '}
            if (email.isEmpty()) {
                Toast.makeText(
                    this@ForgetPasswordActivity,
                    "Please enter email address.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful) {
                            Toast.makeText(
                                this@ForgetPasswordActivity,
                                "Email sent successfully to reset your password.",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        } else {
                            Toast.makeText(
                                this@ForgetPasswordActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }
}