import org.w3c.dom.CanvasRenderingContext2D
import kotlin.math.PI

interface TimelineComponent {
    fun draw(ctx: CanvasRenderingContext2D)
}

class Timeline(
    private val ctx: CanvasRenderingContext2D,
    private val data: TimelineData,
    dim: Vector2D,
    titleConfig: TextConfig,
    xAxisConfig: TextConfig,
    yAxisConfig: TextConfig
) {
    private val title: Title
    private val xAxis: XAxis
    private val yAxis: YAxis
    private val body: Body

    private val children: List<TimelineComponent>

    var dim: Vector2D = dim
        set(value) {
            field = value
            update()
        }

    var viewX: Double
        get() = body.viewX
        set(value) {
            body.viewX = value
        }
    var scale: Double
        get() = body.scale
        set(value) {
            body.scale = value
        }

    init {
        val titleDim = titleConfig.getDim(ctx)
        val xAxisDim = xAxisConfig.getDim(ctx)
        val yAxisDim = yAxisConfig.getDim(ctx).swapXY()

        val titlePos = Vector2D(dim.x / 2, 0.0)
        val xAxisPos = Vector2D(
            (dim.x - yAxisDim.x) / 2 + yAxisDim.x,
            dim.y - xAxisDim.y
        )
        val yAxisPos = Vector2D(
            0.0,
            (dim.y - titleDim.y - xAxisDim.y) / 2 + titleDim.y
        )
        val bodyPos = Vector2D(yAxisDim.x, titleDim.y)

        val bodyDim = Vector2D(
            dim.x - yAxisDim.x,
            dim.y - titleDim.y - xAxisDim.y
        )

        title = Title(titlePos, titleDim, titleConfig)
        xAxis = XAxis(xAxisPos, xAxisDim, xAxisConfig)
        yAxis = YAxis(yAxisPos, yAxisDim, yAxisConfig)
        body = Body(bodyPos, bodyDim)

        children = listOf(body, title, xAxis, yAxis)
    }

    fun draw() {
        ctx.clearRect(0.0, 0.0, dim.x, dim.y)
        for (child in children) {
            child.draw(ctx)
        }
    }

    private fun update() {
        title.pos = Vector2D(dim.x / 2, 0.0)
        xAxis.pos = Vector2D(
            (dim.x - yAxis.dim.x) / 2 + yAxis.dim.x,
            dim.y - xAxis.dim.y
        )
        yAxis.pos = Vector2D(
            0.0,
            (dim.y - title.dim.y - xAxis.dim.y) / 2 + title.dim.y
        )

        body.dim = Vector2D(
            dim.x - yAxis.dim.x,
            dim.y - title.dim.y - xAxis.dim.y
        )

        draw()
    }
}

class Title(
    var pos: Vector2D,
    val dim: Vector2D,
    val textConfig: TextConfig
) : TimelineComponent {
    override fun draw(ctx: CanvasRenderingContext2D) {
        textConfig.setFontAndStyle(ctx)
        ctx.fillText(textConfig.text, pos.x, pos.y)
    }
}

class Body(
    val pos: Vector2D,
    dim: Vector2D
) : TimelineComponent {
    var viewX = 0.0
        set(value) {
            field = value.coerceIn(0.0, 400.0)
            update()
        }
    var scale = 1.0
        set(value) {
            field = value
            update()
        }
    var dim: Vector2D = dim
        set(value) {
            field = value
            update()
        }

    override fun draw(ctx: CanvasRenderingContext2D) {
        with(ctx) {
            fillStyle = "red"
            strokeStyle = "red"
            fillRect(pos.x, pos.y, dim.x, dim.y)
        }
    }

    private fun update() {
    }
}

abstract class Axis(
    var pos: Vector2D,
    val dim: Vector2D,
    val textConfig: TextConfig
) : TimelineComponent

class XAxis(
    pos: Vector2D,
    dim: Vector2D, // top center
    textConfig: TextConfig
) : Axis(pos, dim, textConfig) {
    override fun draw(ctx: CanvasRenderingContext2D) {
        textConfig.setFontAndStyle(ctx)
        ctx.fillText(textConfig.text, pos.x, pos.y)
    }
}

class YAxis(
    pos: Vector2D,
    dim: Vector2D, // left center
    textConfig: TextConfig
) : Axis(pos, dim, textConfig) {
    override fun draw(ctx: CanvasRenderingContext2D) {
        textConfig.setFontAndStyle(ctx)
        ctx.translate(pos.x, pos.y)
        ctx.rotate(-PI / 2)
        ctx.fillText(textConfig.text, 0.0, 0.0)
        ctx.resetTransform()
    }
}
