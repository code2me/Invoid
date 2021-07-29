package com.example.invoid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<TextView>(R.id.register_button)
        val registerEmail = findViewById<TextView>(R.id.register_email)
        val registerPass = findViewById<TextView>(R.id.register_pass)
        val login = findViewById<TextView>(R.id.login)

        login.setOnClickListener {

            onBackPressed()
        }

        registerButton.setOnClickListener {
            when {
                TextUtils.isEmpty(registerEmail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerPass.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = registerEmail.text.toString().trim { it <= ' '}
                    val password: String = registerPass.text.toString().trim { it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You were registered successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
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