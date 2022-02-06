package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.InfoContainer

@Preview(showBackground = true)
@Composable
fun InfoContainerPreview() {
    InfoContainer {
        Text("Children content")
    }
}
