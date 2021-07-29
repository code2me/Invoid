package com.example.invoid

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputEmail = findViewById<TextView>(R.id.input_email)
        val inputPassword = findViewById<TextView>(R.id.input_password)
        val loginButton = findViewById<TextView>(R.id.login_button)
        val forgetPassword = findViewById<TextView>(R.id.forget_pass)
        val register = findViewById<TextView>(R.id.register)

        forgetPassword.setOnClickListener{
            startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
        }

        register.setOnClickListener {

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            when {
                TextUtils.isEmpty(inputEmail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(inputPassword.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = inputEmail.text.toString().trim { it <= ' '}
                    val password: String = inputPassword.text.toString().trim { it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this@LoginActivity,
                                    "You were logged in successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}