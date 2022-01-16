package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.theme.F1ResultsColor

@Composable
fun StandingPosition(position: String) {
    Column(modifier = Modifier.padding(10.dp)) {
        Column(
            modifier = Modifier
                .size(40.dp)
                .border(0.dp, color = Color.Transparent, RoundedCornerShape(20.dp))
                .background(
                    color = when (position) {
                        "1" -> F1ResultsColor.FirstPlace
                        "2" -> F1ResultsColor.SecondPlace
                        "3" -> F1ResultsColor.ThirdPlace
                        else -> Color.Transparent
                    }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                position,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun StandingScore(points: String, wins: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.padding(end = 4.dp)) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("Wins: ")
                    }
                    append(wins)
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Column(
            modifier = Modifier.padding(
                start = 4.dp,
                top = 2.dp,
                end = 10.dp,
                bottom = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .size(30.dp)
                    .border(0.dp, Color.Transparent, RoundedCornerShape(15.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(points, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
