package com.example.ec3

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.ec3.databinding.ActivityAddcardBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class Addcard : AppCompatActivity() {
    private lateinit var binding: ActivityAddcardBinding
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var firestore: FirebaseFirestore
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddcardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore

        binding.imgPhoto.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            openGalleryLauncher.launch(galleryIntent)
        }

        openGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    Glide.with(this).load(it).into(binding.imageView3)
                    uploadImageToFirebase(selectedImageUri)
                }
            }
        }

        binding.btnRegisterCoupon.setOnClickListener {
            uploadImageToFirebase(selectedImageUri)
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri?) {
        if (imageUri != null) {
            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child("images/${UUID.randomUUID()}")
            val uploadTask = imageRef.putFile(imageUri)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val title: String = binding.tilTitle.editText?.text.toString()
                    val genre: String = binding.tilGenre.editText?.text.toString()
                    val description: String = binding.tilGenre.editText?.text.toString()
                    saveCouponToFirestore(downloadUri.toString(),title,genre,description)
                } else {
                    // Manejar el error de obtener la URL
                    Toast.makeText(this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveCouponToFirestore(
        imageUrl: String,
        title: String,
        genre: String,
        description: String
    ) {
        if (title.isNotEmpty()) {
            val coupon = hashMapOf(
                "title" to title,
                "imageUrl" to imageUrl,
                "tipo" to genre,
                "description" to description,
            )
            firestore.collection("Listagames")
                .add(coupon)
                .addOnSuccessListener {
                    Toast.makeText(this, "Agregar correctamente el id: ${it.id}", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "No se agregó el elemento", Toast.LENGTH_SHORT).show()
                }
        }
    }
}