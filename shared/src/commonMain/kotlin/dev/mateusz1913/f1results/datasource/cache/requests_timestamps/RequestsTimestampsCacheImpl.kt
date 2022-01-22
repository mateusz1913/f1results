package dev.mateusz1913.f1results.datasource.cache.requests_timestamps

import dev.mateusz1913.f1results.datasource.cache.Request_timestamp_Entity
import dev.mateusz1913.f1results.datasource.cache.RequestsTimestampsQueries

class RequestsTimestampsCacheImpl(private val requestsTimestampsQueries: RequestsTimestampsQueries) :
    RequestsTimestampsCache {
    override fun getRequestTimestamp(request: String): Request_timestamp_Entity? {
        return requestsTimestampsQueries.getRequestTimestamp(request).executeAsOneOrNull()
    }

    override fun insertRequestTimestamp(request: String, timestamp: Double) {
        requestsTimestampsQueries.insertRequestTimestamp(request, timestamp)
    }
}