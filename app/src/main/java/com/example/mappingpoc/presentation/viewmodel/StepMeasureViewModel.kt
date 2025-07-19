package com.example.mappingpoc.presentation.viewmodel

import com.example.mappingpoc.util.StepSensorManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class StepMeasureViewModel(
    private val stepSensorManager: StepSensorManager
) : ViewModel() {

    private val strideLength = 0.75
    private val path = mutableListOf<Pair<Double, Double>>()
    private var x = 0.0
    private var y = 0.0
    private var direction = 0.0

    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> = _stepCount

    private val _perimeter = MutableStateFlow(0.0)
    val perimeter: StateFlow<Double> = _perimeter

    private val _area = MutableStateFlow(0.0)
    val area: StateFlow<Double> = _area

    private val _mode = MutableStateFlow("")
    val mode: StateFlow<String> = _mode

    fun start(context: Context) {
        _mode.value = stepSensorManager.init(context)
        stepSensorManager.onStepDetected = { step() }
    }

    fun stop() {
        stepSensorManager.stop()
    }

    fun reset() {
        _stepCount.value = 0
        _perimeter.value = 0.0
        _area.value = 0.0
        path.clear()
        x = 0.0
        y = 0.0
    }

    private fun step() {
        viewModelScope.launch {
            _stepCount.value += 1
            x += strideLength * cos(direction)
            y += strideLength * sin(direction)
            path.add(Pair(x, y))
            _perimeter.value = _stepCount.value * strideLength
            if (path.size >= 3) _area.value = shoelaceArea(path)
        }
    }

    private fun shoelaceArea(points: List<Pair<Double, Double>>): Double {
        var sum = 0.0
        for (i in points.indices) {
            val (x1, y1) = points[i]
            val (x2, y2) = points[(i + 1) % points.size]
            sum += (x1 * y2) - (x2 * y1)
        }
        return abs(sum) / 2
    }
}