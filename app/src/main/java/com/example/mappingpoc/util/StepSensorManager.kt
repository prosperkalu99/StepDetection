package com.example.mappingpoc.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.abs
import kotlin.math.sqrt

class StepSensorManager : SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var stepDetector: Sensor? = null
    private var stepCounter: Sensor? = null
    private var accelerometer: Sensor? = null

    private var fallbackMode = "None"
    private var baselineStep = -1f
    private var prevMag = 0f

    var onStepDetected: (() -> Unit)? = null

    fun init(context: Context): String {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetector = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        stepCounter = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        fallbackMode = when {
            stepDetector != null -> {
                sensorManager?.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_UI)
                "StepDetector"
            }
            stepCounter != null -> {
                sensorManager?.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI)
                "StepCounter"
            }
            accelerometer != null -> {
                sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
                "Accelerometer"
            }
            else -> "Unsupported"
        }
        Log.d("SensorManager", "Using Mode: $fallbackMode")
        return fallbackMode
    }

    fun stop() {
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_STEP_DETECTOR -> onStepDetected?.invoke()
            Sensor.TYPE_STEP_COUNTER -> {
                val steps = event.values[0]
                if (baselineStep < 0f) baselineStep = steps
                val current = (steps - baselineStep).toInt()
                repeat(current) { onStepDetected?.invoke() }
                baselineStep += current
            }
            Sensor.TYPE_ACCELEROMETER -> {
                val mag = sqrt(event.values.map { it.toDouble() }.sumOf { it * it }).toFloat()
                val delta = abs(mag - prevMag)
                if (delta > 2f) onStepDetected?.invoke()
                prevMag = mag
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}