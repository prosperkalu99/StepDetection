package com.example.mappingpoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mappingpoc.presentation.screens.StepMeasureScreen
import com.example.mappingpoc.ui.theme.MappingPOCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MappingPOCTheme {
                StepMeasureScreen()
            }
        }
    }
}