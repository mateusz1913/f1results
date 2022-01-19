package dev.mateusz1913.f1results.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.Koin
import dev.mateusz1913.f1results.android.presentation.navigation.Navigation
import dev.mateusz1913.f1results.android.utils.NetworkMonitor
import dev.mateusz1913.f1results.composable.theme.F1ResultsTheme
import dev.mateusz1913.f1results.di.initKoinAppDeclaration
import org.koin.android.ext.koin.androidContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            Koin(appDeclaration = {
                androidContext(this@MainActivity)
                initKoinAppDeclaration(this)
            }) {
                val systemUiController = rememberSystemUiController()
                F1ResultsTheme {
                    val statusBarColor = MaterialTheme.colors.primary
                    val useDarkIcons = MaterialTheme.colors.isLight
                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = statusBarColor,
                            darkIcons = useDarkIcons
                        )
                    }
                    Navigation()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        NetworkMonitor.shared.startMonitoring(this)
    }

    override fun onPause() {
        super.onPause()
        NetworkMonitor.shared.stopMonitoring()
    }
}
