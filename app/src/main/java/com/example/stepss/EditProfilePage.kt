package com.example.stepss

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class EditProfilePage : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var editProfileImageButton: ImageButton

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextAddress: EditText

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

        editTextName = findViewById(R.id.edit_name)
        editTextPassword = findViewById(R.id.edit_password)
        editTextEmail = findViewById(R.id.edit_email)
        editTextContact = findViewById(R.id.edit_contact)
        editTextAddress = findViewById(R.id.edit_location)

        val btnSave = findViewById<Button>(R.id.save_button)
        val backArrow: ImageButton = findViewById(R.id.back_button)

        // Get current data passed from previous page
        val currentName = intent.getStringExtra("CURRENT_NAME")
        val currentPassword = intent.getStringExtra("CURRENT_PASSWORD")
        val currentEmail = intent.getStringExtra("CURRENT_EMAIL")
        val currentContact = intent.getStringExtra("CURRENT_CONTACT")
        val currentLocation = intent.getStringExtra("CURRENT_LOCATION")

        // Set the data to the edit text fields
        editTextName.setText(currentName)
        editTextPassword.setText(currentPassword)
        editTextEmail.setText(currentEmail)
        editTextContact.setText(currentContact)
        editTextAddress.setText(currentLocation)

        // Back button
        backArrow.setOnClickListener { redirectToProfilePage() }

        // Change profile photo
        editProfileImageButton.setOnClickListener { showImagePickerOptions() }

        // Save button click
        btnSave.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("NEW_NAME", editTextName.text.toString())
                putExtra("NEW_PASSWORD", editTextPassword.text.toString())
                putExtra("NEW_EMAIL", editTextEmail.text.toString())
                putExtra("NEW_CONTACT", editTextContact.text.toString())
                putExtra("NEW_LOCATION", editTextAddress.text.toString())
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
