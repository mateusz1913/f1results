import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.mateusz1913.f1results.Greeting
import dev.mateusz1913.f1results.repository.F1Repository
import dev.mateusz1913.f1results.repository.models.driver.DriverType

fun greet(): String {
    return Greeting().greeting()
}

@Composable
fun Screen() {
    var driverState by remember { mutableStateOf<DriverType?>(null) }
    LaunchedEffect(key1 = null, block = {
        val driver = F1Repository.api.driversApi.getSpecificDriver("alonso")
        if (driver != null) {
            driverState = driver
        }
    })
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = greet(), color = MaterialTheme.colors.primary)
            val driver = driverState
            if (driver != null) {
                Text(text = "${driver.givenName} ${driver.familyName}", color = MaterialTheme.colors.secondary)
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            Screen()
        }
    }
}
