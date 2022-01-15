package dev.mateusz1913.f1results.composable.common

import androidx.compose.runtime.*

@Composable
expect fun DropdownButton(items: List<String>, selected: String, onSelectedChange: (newSelected: String) -> Unit)
