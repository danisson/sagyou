package sagyou.view

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import sagyou.extensions.toTimeFormat
import sagyou.model.WorkHoursRepository
import sagyou.model.WorkInterval
import tornadofx.*
import java.time.Duration
import java.time.LocalDateTime

class MainView : View("Sagyou") {
    private var duration = WorkHoursRepository.getTodaysWorkDuration()
    private var startTime = LocalDateTime.MAX!!

    private val screenCounter = SimpleStringProperty(duration.toTimeFormat())
    private val incrementTimeline = Timeline(
            KeyFrame(javafx.util.Duration.seconds(1.0), EventHandler<ActionEvent> {
                val extra = Duration.between(startTime, LocalDateTime.now())
                screenCounter.set(duration.plus(extra).toTimeFormat())
            })
    )

    init {
        incrementTimeline.cycleCount = Timeline.INDEFINITE
    }

    override val root = borderpane {
        style {
            padding = box(20.px)
        }

        top = label {
            useMaxWidth = true
            alignment = Pos.CENTER
            textProperty().bind(screenCounter)
        }

        center = togglebutton {
            isSelected = false
            val stateText = selectedProperty().stringBinding {
                if (it == true) "作業中" else "作業"
            }
            textProperty().bind(stateText)
            selectedProperty().addListener { _, _, newValue ->
                if (newValue) {
                    startTime = LocalDateTime.now()
                    incrementTimeline.play()
                } else {
                    incrementTimeline.stop()
                    val interval = WorkInterval(startTime)
                    WorkHoursRepository.addWorkInterval(interval)
                    duration = duration.plus(interval.duration)
                    screenCounter.set(duration.toTimeFormat())
                    println(WorkHoursRepository.repository)
                }
            }
        }
    }
}
