package dev.mateusz1913.f1results.datasource.cache.requests_timestamps

import dev.mateusz1913.f1results.datasource.cache.Request_timestamp_Entity

interface RequestsTimestampsCache {
    fun getRequestTimestamp(request: String): Request_timestamp_Entity?
    fun insertRequestTimestamp(request: String, timestamp: Double)
}
