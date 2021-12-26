package dev.mateusz1913.f1results.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.mateusz1913.f1results.Greeting
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import dev.mateusz1913.f1results.android.theme.F1ResultsColor
import dev.mateusz1913.f1results.android.theme.F1ResultsTheme

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1ResultsTheme {
                Text(text = greet(), color = F1ResultsColor.Primary)
            }
        }
    }
}
