package dev.mateusz1913.f1results.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.mateusz1913.f1results.android.presentation.navigation.Navigation
import dev.mateusz1913.f1results.android.theme.F1ResultsTheme
import dev.mateusz1913.f1results.di.AppInfo
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@ExperimentalPagerApi
class MainActivity : AppCompatActivity(), KoinComponent {
    private val appInfo: AppInfo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.d("APP_INFO: ${appInfo.appId}", null, "appInfo")
        setContent {
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
