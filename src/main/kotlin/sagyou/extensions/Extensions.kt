package sagyou.extensions

import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

fun Duration.toTimeFormat() = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60))
fun LocalDate.atEndOfDay() = atTime(LocalTime.MAX)
