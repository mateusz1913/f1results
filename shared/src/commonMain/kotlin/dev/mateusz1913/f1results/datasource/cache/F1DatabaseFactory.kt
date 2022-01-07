package dev.mateusz1913.f1results.datasource.cache

class F1DatabaseFactory(private val driverFactory: DatabaseDriverFactory) {
    fun createDatabase(): F1Database {
        val driver = driverFactory.createDriver()
        return F1Database(driver)
    }
}
