package dev.mateusz1913.f1results.composable.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MapboxMapView(modifier: Modifier = Modifier, latitude: Double, longitude: Double)
