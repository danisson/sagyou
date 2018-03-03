package sagyou

import javafx.stage.Stage
import sagyou.view.MainView
import tornadofx.*

class Sagyou: App(MainView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        stage.width = 256.0
        stage.height = 256.0
        super.start(stage)
    }
}
