package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoRow(
    label: String,
    value: String,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null
) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                append(label)
            }
            append(value)
        },
        fontSize = fontSize ?: 16.sp,
        fontWeight = fontWeight,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}
