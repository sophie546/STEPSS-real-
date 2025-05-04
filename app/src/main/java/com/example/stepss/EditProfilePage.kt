package com.example.stepss

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import androidx.appcompat.app.AppCompatActivity

class EditProfilePage : AppCompatActivity() {
    private lateinit var profileImageView: ImageView
    private lateinit var editProfileImageButton: ImageButton
    private var currentPhotoUri: Uri? = null

    // Gallery launcher
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            profileImageView.setImageURI(uri)
            currentPhotoUri = uri
        }
    }

    // Camera launcher
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && currentPhotoUri != null) {
            profileImageView.setImageURI(currentPhotoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_page)

        // Initialize views
        profileImageView = findViewById(R.id.profile_image)
        editProfileImageButton = findViewById(R.id.profile_image_view)
        val editTextName = findViewById<EditText>(R.id.edit_name)
        val editTextEmail = findViewById<EditText>(R.id.edit_email)
        val editTextTitle = findViewById<EditText>(R.id.edit_title)
        val editTextAddress = findViewById<EditText>(R.id.edit_location)
        val btnSave = findViewById<Button>(R.id.save_button)
        val backArrow: ImageButton = findViewById(R.id.back_button)

        // Set click listeners
        backArrow.setOnClickListener { redirectToProfilePage() }
        editProfileImageButton.setOnClickListener { showImagePickerOptions() }

        btnSave.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("NEW_NAME", editTextName.text.toString())
                putExtra("NEW_EMAIL", editTextEmail.text.toString())
                putExtra("NEW_TITLE", editTextTitle.text.toString())
                putExtra("NEW_ADDRESS", editTextAddress.text.toString())
                currentPhotoUri?.let { uri -> putExtra("PROFILE_IMAGE_URI", uri.toString()) }
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun showImagePickerOptions() {
        AlertDialog.Builder(this).apply {
            setTitle("Change Profile Photo")
            setItems(arrayOf("Take Photo", "Choose from Gallery", "Cancel")) { _, which ->
                when (which) {
                    0 -> takePhoto()
                    1 -> chooseFromGallery()
                    2 -> {} // Cancel
                }
            }
            show()
        }
    }

    private fun takePhoto() {
        createImageFile().let { file ->
            FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                file
            ).also { uri ->
                currentPhotoUri = uri
                cameraLauncher.launch(uri)
            }
        }
    }

    private fun chooseFromGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun createImageFile(): File {
        return File.createTempFile(
            "JPEG_profile_${System.currentTimeMillis()}_",
            ".jpg",
            getExternalFilesDir(null)
        )
    }

    private fun redirectToProfilePage() {
        Intent(this, ProfilePage::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
            finish()
        }
    }
}