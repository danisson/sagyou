package sagyou.view

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import tornadofx.*
import java.time.Duration
import java.time.Instant

class MainView : View("Sagyou") {
    private var duration = Duration.ZERO!!
    private var startTime = Instant.MAX!!

    private val screenCounter = SimpleStringProperty("0 s")
    private val incrementTimeline = Timeline(
            KeyFrame(javafx.util.Duration.seconds(1.0), EventHandler<ActionEvent> {
                val extra = Duration.between(startTime, Instant.now())
                screenCounter.set("${duration.plus(extra).seconds} s")
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
                    startTime = Instant.now()
                    incrementTimeline.play()
                } else {
                    incrementTimeline.stop()
                    val extra = Duration.between(startTime, Instant.now())
                    duration = duration.plus(extra)
                    screenCounter.set("${duration.seconds} s")
                }
            }
        }
    }
}
