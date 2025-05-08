package com.example.stepss

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.stepss.data.ProfileData
import java.io.File

class EditProfilePage : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var editProfileImageButton: ImageView

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextAddress: EditText

    private var currentPhotoUri: Uri? = null

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            profileImageView.setImageURI(uri)
            currentPhotoUri = uri
        } else {
            currentPhotoUri = null
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && currentPhotoUri != null) {
            profileImageView.setImageURI(currentPhotoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_page)

        profileImageView = findViewById(R.id.profile_image)
        editProfileImageButton = findViewById(R.id.profile_image_view)

        editTextName = findViewById(R.id.edit_name)
        editTextEmail = findViewById(R.id.edit_email)
        editTextPassword = findViewById(R.id.edit_password)
        editTextContact = findViewById(R.id.edit_contact)
        editTextAddress = findViewById(R.id.edit_location)

        val btnSave = findViewById<Button>(R.id.save_button)
        val backArrow: ImageButton = findViewById(R.id.back_button)

        val profileData = intent.getParcelableExtra<ProfileData>("PROFILE_DATA")
        if (profileData == null) {
            // Handle the error gracefully, maybe show a message or handle the missing data scenario
            Toast.makeText(this, "Profile data is missing!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            // Proceed with setting the data
            editTextName.setText(profileData.name)
            editTextPassword.setText(profileData.password)
            editTextEmail.setText(profileData.email)
            editTextContact.setText(profileData.contact)
            editTextAddress.setText(profileData.location)
            profileData.imageUri?.let {
                profileImageView.setImageURI(it)
                currentPhotoUri = it
            }
        }

        backArrow.setOnClickListener { redirectToProfilePage() }

        editProfileImageButton.setOnClickListener { showImagePickerOptions() }

        btnSave.setOnClickListener {
            val updatedProfileData = ProfileData(
                name = editTextName.text.toString(),
                password = editTextPassword.text.toString(),
                email = editTextEmail.text.toString(),
                contact = editTextContact.text.toString(),
                location = editTextAddress.text.toString(),
                imageUri = currentPhotoUri
            )

            saveUserData(updatedProfileData)

            val resultIntent = Intent().apply {
                putExtra("UPDATED_PROFILE_DATA", updatedProfileData)
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
        Intent(this, ProfilePage2::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun saveUserData(profileData: ProfileData) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("USERNAME", profileData.name)
        editor.putString("PASSWORD", profileData.password)
        editor.putString("EMAIL", profileData.email)
        editor.putString("CONTACT", profileData.contact)
        editor.putString("LOCATION", profileData.location)
        editor.putString("PROFILE_IMAGE_URI", profileData.imageUri?.toString())

        editor.apply()
    }
}
