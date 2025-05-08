package com.example.stepss

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.stepss.data.ProfileData
import java.io.File
import java.io.IOException

class EditProfilePage : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var editProfileImageButton: ImageView
    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextAddress: EditText

    private var currentPhotoUri: Uri? = null
    private var currentPhotoFile: File? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            loadImageWithGlide(uri)
            currentPhotoUri = uri
            currentPhotoFile = null
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && currentPhotoUri != null) {
            loadImageWithGlide(currentPhotoUri!!)
        } else {
            currentPhotoFile?.delete()
            currentPhotoUri = null
            currentPhotoFile = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_page)

        initializeViews()
        loadProfileData()
        setupClickListeners()
    }

    private fun initializeViews() {
        profileImageView = findViewById(R.id.profile_image)
        editProfileImageButton = findViewById(R.id.profile_image_view)
        editTextName = findViewById(R.id.edit_name)
        editTextEmail = findViewById(R.id.edit_email)
        editTextPassword = findViewById(R.id.edit_password)
        editTextContact = findViewById(R.id.edit_contact)
        editTextAddress = findViewById(R.id.edit_location)
    }

    private fun loadProfileData() {
        val profileData = intent.getParcelableExtra<ProfileData>("PROFILE_DATA") ?: run {
            Toast.makeText(this, "Profile data is missing!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        editTextName.setText(profileData.name)
        editTextPassword.setText(profileData.password)
        editTextEmail.setText(profileData.email)
        editTextContact.setText(profileData.contact)
        editTextAddress.setText(profileData.location)

        profileData.imageUri?.let { uri ->
            if (isUriValid(uri)) {
                loadImageWithGlide(uri)
                currentPhotoUri = uri
            } else {
                setDefaultProfileImage()
            }
        } ?: setDefaultProfileImage()
    }

    private fun setupClickListeners() {
        findViewById<ImageButton>(R.id.back_button).setOnClickListener { redirectToProfilePage() }
        editProfileImageButton.setOnClickListener { showImagePickerOptions() }
        findViewById<Button>(R.id.save_button).setOnClickListener { saveProfile() }
    }

    private fun loadImageWithGlide(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .circleCrop()
            .error(R.drawable.profile_picture)
            .into(profileImageView)
    }

    private fun setDefaultProfileImage() {
        Glide.with(this)
            .load(R.drawable.profile_picture)
            .circleCrop()
            .into(profileImageView)
        currentPhotoUri = null
        currentPhotoFile = null
    }

    private fun isUriValid(uri: Uri): Boolean {
        return try {
            contentResolver.openInputStream(uri)?.use { it.read() }
            true
        } catch (e: Exception) {
            Log.e("EditProfile", "Invalid URI: ${e.message}")
            false
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
        try {
            createImageFile().let { file ->
                currentPhotoFile = file
                FileProvider.getUriForFile(
                    this,
                    "${packageName}.fileprovider",
                    file
                ).also { uri ->
                    currentPhotoUri = uri
                    cameraLauncher.launch(uri)
                }
            }
        } catch (ex: IOException) {
            Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show()
            Log.e("EditProfile", "Error creating image file", ex)
        }
    }

    private fun chooseFromGallery() {
        galleryLauncher.launch("image/*")
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create a dedicated subdirectory for your app's pictures
        val storageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Stepss_Profile")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        return File.createTempFile(
            "JPEG_profile_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        ).apply {
            createNewFile()
            Log.d("EditProfile", "Created image file at: ${absolutePath}")
        }
    }

    private fun saveProfile() {
        val imageUriToSave = currentPhotoUri?.takeIf { isUriValid(it) }

        val updatedProfileData = ProfileData(
            name = editTextName.text.toString(),
            password = editTextPassword.text.toString(),
            email = editTextEmail.text.toString(),
            contact = editTextContact.text.toString(),
            location = editTextAddress.text.toString(),
            imageUri = imageUriToSave
        )

        saveUserData(updatedProfileData)

        setResult(RESULT_OK, Intent().apply {
            action = "PROFILE_UPDATED"
            putExtra("UPDATED_PROFILE_DATA", updatedProfileData)
        })
        finish()
    }

    private fun saveUserData(profileData: ProfileData) {
        getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit().apply {
            putString("USERNAME", profileData.name)
            putString("PASSWORD", profileData.password)
            putString("EMAIL", profileData.email)
            putString("CONTACT", profileData.contact)
            putString("LOCATION", profileData.location)

            if (profileData.imageUri != null && isUriValid(profileData.imageUri!!)) {
                putString("PROFILE_IMAGE_URI", profileData.imageUri.toString())
                Log.d("EditProfile", "Saved image URI: ${profileData.imageUri}")
            } else {
                remove("PROFILE_IMAGE_URI")
                Log.d("EditProfile", "Removed invalid image URI")
            }
            apply()
        }
    }

    private fun redirectToProfilePage() {
        startActivity(Intent(this, ProfilePage2::class.java))
        finish()
    }
}