package dev.mateusz1913.f1results.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.mateusz1913.f1results.Greeting
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.mateusz1913.f1results.android.theme.F1ResultsColor
import dev.mateusz1913.f1results.android.theme.F1ResultsTheme
import dev.mateusz1913.f1results.repository.F1Repository
import dev.mateusz1913.f1results.repository.models.driver.DriverType

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1ResultsTheme {
                Screen()
            }
        }
    }
}

@Composable
fun Screen() {
    var driverState by remember { mutableStateOf<DriverType?>(null) }
    LaunchedEffect(key1 = null, block = {
        val driverInfoResponse = F1Repository.api.driversApi.getSpecificDriver("alonso")
        if (driverInfoResponse.MRData.DriverTable.Drivers.isNotEmpty()) {
            val driver = driverInfoResponse.MRData.DriverTable.Drivers[0]
            driverState = driver
        }
    })
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = greet(), color = F1ResultsColor.Primary)
            val driver = driverState
            if (driver != null) {
                Text(text = "${driver.givenName} ${driver.familyName}", color = F1ResultsColor.Secondary)
            }
        }
    }
}
