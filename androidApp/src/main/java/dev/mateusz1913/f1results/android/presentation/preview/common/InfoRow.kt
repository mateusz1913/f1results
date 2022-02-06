package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.common.InfoRow

@Preview(showBackground = true)
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
