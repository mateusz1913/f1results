package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.common.InfoRow

@Preview
@Composable
fun InfoRowPreview() {
    Column {
        InfoRow(label = "First label: ", value = "First value")
        InfoRow(
            label = "Second label: ",
            value = "Second value",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
