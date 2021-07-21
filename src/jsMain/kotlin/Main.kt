import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

private object Main : InputListener {
    private val timeline: Timeline
    private val canvas: HTMLCanvasElement

    init {
        console.log("initializing")

        canvas = document.getElementById("timelineCanvas") as HTMLCanvasElement
        val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

        timeline = Timeline(
            ctx,
            dim = Vector2D(canvas.width, canvas.height),
            titleConfig = TextConfig(
                text = "Example Title",
                font = "40px serif"
            ),
            xAxisConfig = TextConfig(
                text = "X-Axis",
                font = "20px serif"
            ),
            yAxisConfig = TextConfig(
                text = "Y-Axis",
                font = "20px serif"
            )
        )

        InputHandler(this, document.body!!.style, canvas, document)
    }

    fun start() {
        console.log("starting")
//        console.log(require("../../../../processedResources/js/main/test.txt"))
//        console.log(require("./test.txt"))
        console.log(jsonClient)
        timeline.draw()
    }

    override fun onDragging(dist: Vector2D) {
        timeline.viewX -= dist.x
        timeline.draw()
    }

//    override fun onRightClick() {
//        console.log("right click")
//
//        canvas.width = window.innerWidth
//        canvas.height = window.innerHeight
//        timeline.dim = Vector2D(canvas.width, canvas.height)
//    }
}

fun main() {
    Main.start()
}

external val require: dynamic
