package dev.mateusz1913.f1results

import io.ktor.client.*
import io.ktor.client.engine.ios.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

actual fun createKtorClient(): HttpClient {
    val client = HttpClient(Ios) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                }
            )
        }
    }
    return client
}
