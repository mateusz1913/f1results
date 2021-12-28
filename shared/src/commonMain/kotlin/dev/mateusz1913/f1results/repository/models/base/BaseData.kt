package dev.mateusz1913.f1results.repository.models.base

interface BaseData {
    val series: String?
    val url: String?
    val limit: Int?
    val offset: Int?
    val total: Int?
}