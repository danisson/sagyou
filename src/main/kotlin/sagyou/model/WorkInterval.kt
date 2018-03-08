package sagyou.model

import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WorkInterval {
    val start: LocalDateTime
    val finish: LocalDateTime
    val duration: Duration get() = Duration.between(start, finish)

    constructor(start: LocalDateTime, finish: LocalDateTime = LocalDateTime.now()) {
        this.start = start.truncatedTo(ChronoUnit.SECONDS)
        this.finish = finish.truncatedTo(ChronoUnit.SECONDS)
    }

    override fun toString() = "WorkInterval($start, $finish)"
}
