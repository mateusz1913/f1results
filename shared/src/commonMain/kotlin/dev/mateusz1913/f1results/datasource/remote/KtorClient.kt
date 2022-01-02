package dev.mateusz1913.f1results.datasource.remote

import io.ktor.client.*

expect fun createKtorClient(): HttpClient
