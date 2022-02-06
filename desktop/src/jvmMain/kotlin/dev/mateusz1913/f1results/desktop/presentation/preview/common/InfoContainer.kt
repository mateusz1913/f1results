package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.InfoContainer

@Preview
@Composable
fun InfoContainerPreview() {
    InfoContainer {
        Text("Children content")
    }
}
