package dev.mateusz1913.f1results.domain

import kotlinx.datetime.*

fun now(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)

fun LocalDateTime.toEpochMilliseconds(): Double = this.toInstant(TimeZone.UTC).toEpochMilliseconds().toDouble()
