package com.example.mappingpoc.presentation.screens

import com.example.mappingpoc.presentation.viewmodel.StepMeasureViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun StepMeasureScreen(viewModel: StepMeasureViewModel = koinViewModel()) {
    val context = LocalContext.current

    val steps by viewModel.stepCount.collectAsStateWithLifecycle()
    val perimeter by viewModel.perimeter.collectAsStateWithLifecycle()
    val area by viewModel.area.collectAsStateWithLifecycle()
    val mode by viewModel.mode.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.start(context) }
    DisposableEffect(Unit) { onDispose { viewModel.stop() } }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mode: $mode", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("Steps: $steps")
        Spacer(Modifier.height(8.dp))
        Text("Perimeter: %.2f m".format(perimeter))
        Spacer(Modifier.height(8.dp))
        Text("Area: %.2f m²".format(area))
        Spacer(Modifier.height(24.dp))
        Button(onClick = { viewModel.reset() }) { Text("Reset") }
    }
}