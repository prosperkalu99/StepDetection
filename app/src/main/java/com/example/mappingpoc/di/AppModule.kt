package com.example.mappingpoc.di

import com.example.mappingpoc.util.StepSensorManager
import com.example.mappingpoc.presentation.viewmodel.StepMeasureViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { StepSensorManager() }
    viewModel { StepMeasureViewModel(get()) }
}