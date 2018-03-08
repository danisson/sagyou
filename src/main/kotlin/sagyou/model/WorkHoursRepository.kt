package sagyou.model

import sagyou.extensions.atEndOfDay
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

object WorkHoursRepository {
    val repository = mutableListOf<WorkInterval>()

    fun addWorkInterval(workInterval: WorkInterval) {
        repository.add(workInterval)
    }

    fun getTodaysWorkDuration(): Duration {
        val now = LocalDate.now()
        return repository
            .filter { it.start.between(now) && it.finish.between(now) }
            .map { WorkInterval(maxOf(it.start, now.atStartOfDay()), minOf(it.finish, now.atEndOfDay())) }
            .fold(Duration.ZERO) { duration: Duration, workInterval: WorkInterval ->
                duration.plus(workInterval.duration)
            }
    }
}

private fun LocalDateTime.between(now: LocalDate) = this <= now.atEndOfDay() && this >= now.atStartOfDay()
