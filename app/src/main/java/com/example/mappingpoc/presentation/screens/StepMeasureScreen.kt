package com.example.mappingpoc.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.mappingpoc.presentation.viewmodel.StepMeasureViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun StepMeasureScreen(viewModel: StepMeasureViewModel = koinViewModel()) {
    val context = LocalContext.current

    val steps by viewModel.stepCount.collectAsStateWithLifecycle()
    val perimeter by viewModel.perimeter.collectAsStateWithLifecycle()
    val area by viewModel.area.collectAsStateWithLifecycle()
    val mode by viewModel.mode.collectAsStateWithLifecycle()

    ActivityRecognitionPermission {
        viewModel.start(context)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stop()
        }
    }

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

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ActivityRecognitionPermission(
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val permission = Manifest.permission.ACTIVITY_RECOGNITION
    val permissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionState.value = granted
        if (granted) onPermissionGranted()
    }

    LaunchedEffect(Unit) {
        if (!permissionState.value) {
            launcher.launch(permission)
        } else if (permissionState.value) {
            onPermissionGranted()
        }
    }

    if (!permissionState.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Activity Recognition permission required to measure steps",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}