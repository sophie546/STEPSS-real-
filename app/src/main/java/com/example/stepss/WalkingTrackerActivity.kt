package com.example.stepss

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class WalkingTrackerActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private var isSensorPresent = false
    private var initialSteps = 0
    private var currentSteps = 0
    private var isCounting = false
    private var isTrackingVisible = false

    private lateinit var tvStepCount: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvSensorStatus: TextView
    private lateinit var btnBack: ImageView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnShowTrack: Button
    private lateinit var btnReset: Button
    private lateinit var imgWalkingGif: ImageView

    companion object {
        private const val ACTIVITY_RECOGNITION_REQUEST = 1
        private const val AVERAGE_STEP_LENGTH_M = 0.762 // Average step length in meters (30 inches)
        private const val METERS_TO_KM = 1000.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walking_tracker)

        // Initialize views
        initializeViews()
        setupGif()
        setupSensorManager()
        setupButtonListeners()
        checkPermissions()
    }

    private fun initializeViews() {
        tvStepCount = findViewById(R.id.tvWalkingStepCount)
        tvSensorStatus = findViewById(R.id.tvWalkingSensorStatus)
        tvDistance = findViewById(R.id.tvDistance)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnShowTrack = findViewById(R.id.btnShowTrack)
        btnReset = findViewById(R.id.btnReset)
        imgWalkingGif = findViewById(R.id.imgWalkingGif)
        btnBack = findViewById(R.id.btnBack)

        // Disable buttons initially
        btnStop.isEnabled = false
        btnShowTrack.isEnabled = false
        btnReset.isEnabled = false
    }

    private fun setupGif() {
        Glide.with(this)
            .asGif()
            .load(R.drawable.walking_addpage)
            .into(imgWalkingGif)
    }

    private fun setupSensorManager() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    private fun setupButtonListeners() {
        btnStart.setOnClickListener { startCounting() }
        btnStop.setOnClickListener { stopCounting() }
        btnShowTrack.setOnClickListener {
            if (isCounting) showProgressActivity()
            else toggleTrackVisibility()
        }
        btnReset.setOnClickListener { resetStepCount() }
        btnBack.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    ACTIVITY_RECOGNITION_REQUEST
                )
            } else {
                setupStepCounter()
            }
        } else {
            setupStepCounter()
        }
    }

    private fun setupStepCounter() {
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        isSensorPresent = stepSensor != null

        if (isSensorPresent) {
            tvSensorStatus.text = "Status: Ready - Click 'Start'"
            btnStart.isEnabled = true
        } else {
            tvSensorStatus.text = "Status: Sensor not available"
            btnStart.isEnabled = false
            Toast.makeText(this, "Step counter sensor not available", Toast.LENGTH_LONG).show()
        }
    }

    private fun startCounting() {
        if (!isCounting && isSensorPresent) {
            if (stepSensor == null) {
                Toast.makeText(this, "Step sensor unavailable", Toast.LENGTH_SHORT).show()
                return
            }

            isCounting = true
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            btnShowTrack.isEnabled = true
            btnReset.isEnabled = false
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
            tvSensorStatus.text = "Status: Counting steps..."
            Toast.makeText(this, "Step counting started", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopCounting() {
        if (isCounting) {
            isCounting = false
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnShowTrack.isEnabled = true
            btnReset.isEnabled = true
            sensorManager.unregisterListener(this)
            tvSensorStatus.text = "Status: Ready"
            Toast.makeText(this, "Step counting stopped", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleTrackVisibility() {
        isTrackingVisible = !isTrackingVisible
        if (isTrackingVisible) {
            tvStepCount.visibility = TextView.VISIBLE
            btnShowTrack.text = "Hide Track"
            Toast.makeText(this, "Showing progress", Toast.LENGTH_SHORT).show()
        } else {
            tvStepCount.visibility = TextView.INVISIBLE
            btnShowTrack.text = "Show Track"
            Toast.makeText(this, "Hiding progress", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetStepCount() {
        if (isCounting) {
            Toast.makeText(this, "Stop counting first to reset", Toast.LENGTH_SHORT).show()
            return
        }

        initialSteps = currentSteps
        updateStepCountDisplay()
        btnReset.isEnabled = false
        isTrackingVisible = false
        tvStepCount.visibility = TextView.INVISIBLE
        btnShowTrack.text = "Show Track"
        btnShowTrack.isEnabled = false
        Toast.makeText(this, "Counter reset to zero", Toast.LENGTH_SHORT).show()
    }


    private fun updateStepCountDisplay() {
        val stepsSinceReset = currentSteps - initialSteps
        tvStepCount.text = stepsSinceReset.toString()

        // Calculate distance in kilometers
        val distanceKm = (stepsSinceReset * AVERAGE_STEP_LENGTH_M) / METERS_TO_KM
        val df = DecimalFormat("#.##")
        tvDistance.text = "${df.format(distanceKm)} km"
    }

    private fun showProgressActivity() {
        val stepsSinceReset = currentSteps - initialSteps
        val distanceKm = (stepsSinceReset * AVERAGE_STEP_LENGTH_M) / METERS_TO_KM
        val df = DecimalFormat("#.##")

        val intent = Intent(this, ActivityProgress::class.java).apply {
            putExtra("STEP_COUNT", stepsSinceReset.toString())
            putExtra("DISTANCE", df.format(distanceKm))
        }
        startActivity(intent)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                currentSteps = it.values[0].toInt()
                if (initialSteps == 0) {
                    initialSteps = currentSteps
                }
                updateStepCountDisplay()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Optional accuracy handling
    }

    override fun onPause() {
        super.onPause()
        if (isCounting) {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isCounting && isSensorPresent) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACTIVITY_RECOGNITION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupStepCounter()
            } else {
                tvSensorStatus.text = "Status: Permission denied"
                btnStart.isEnabled = false
                Toast.makeText(
                    this,
                    "Step counting requires activity recognition permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}