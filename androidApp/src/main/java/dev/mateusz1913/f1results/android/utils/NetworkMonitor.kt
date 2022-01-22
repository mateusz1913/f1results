package dev.mateusz1913.f1results.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NetworkMonitor private constructor() {
    private val _state = MutableStateFlow(NetworkMonitorState())
    val state: StateFlow<NetworkMonitorState> = _state
    private var monitor: ConnectivityManager? = null
    private val callback = object: ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _state.update { it.copy(isReachable = true) }
        }

        override fun onLost(network: Network) {
            _state.update { it.copy(isReachable = false) }
        }
    }

    fun startMonitoring(context: Context) {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            monitor = connectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(callback)
                if (connectivityManager.activeNetwork == null) {
                    _state.update { it.copy(isReachable = false) }
                }
            } else {
                val networkRequest = NetworkRequest.Builder().apply {
                    addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    }
                }.build()
                connectivityManager.registerNetworkCallback(networkRequest, callback)
                val activeNetwork = connectivityManager.activeNetworkInfo
                if (activeNetwork == null || (
                    activeNetwork.type != ConnectivityManager.TYPE_ETHERNET &&
                    activeNetwork.type != ConnectivityManager.TYPE_WIFI &&
                    activeNetwork.type != ConnectivityManager.TYPE_MOBILE
                )) {
                    _state.update { it.copy(isReachable = false) }
                }
            }
        } catch (e: Exception) {
            Napier.w("${e.message}", e, "NetworkMonitor")
            _state.update { it.copy(isReachable = false) }
        }
    }

    fun stopMonitoring() {
        monitor?.unregisterNetworkCallback(callback)
    }

    companion object {
        val shared = NetworkMonitor()
    }
}

data class NetworkMonitorState(
    val isReachable: Boolean = false
)
