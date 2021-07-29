package com.example.invoid

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage


class MainActivity : AppCompatActivity() {

    var imagePath: Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        val txtUserId: TextView = findViewById(R.id.txt_user_id)
        val txtEmailId: TextView = findViewById(R.id.txt_email_id)
        val logoutButton: TextView = findViewById(R.id.logout_button)

        txtUserId.text = "User ID :: $userId"
        txtEmailId.text = "Email ID :: $emailId"

        val selectBtn: TextView = findViewById(R.id.select_button)
        val uploadBtn: TextView = findViewById(R.id.upload_button)

        var uploadedImg: ImageView = findViewById(R.id.uploaded_img)
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                uploadedImg.setImageURI(it)
                imagePath = it
            }
        )

        selectBtn.setOnClickListener {
            getImage.launch("image/*")
        }

        uploadBtn.setOnClickListener {
            val mStorageRef = FirebaseStorage.getInstance().reference
            val uploadTask = mStorageRef.child("${emailId}/idCard.jpg").putFile(imagePath!!)

            val customProgressDialog = Dialog(this)
            customProgressDialog.setContentView(R.layout.dialog_custom_progress)
            customProgressDialog.show()

            uploadTask.addOnSuccessListener {
                imagePath = null
                Toast.makeText(
                    this,
                    "Uploaded Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                customProgressDialog.dismiss()
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error Occur While uploading",
                    Toast.LENGTH_SHORT
                ).show()
                customProgressDialog.dismiss()
            }
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            Toast.makeText(
                this,
                "Logout Successfully",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}
