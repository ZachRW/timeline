package timelinejs

import timelinejs.rendering.Vector2D
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Legend(
    private val data: JsTimelineData,
    private val pos: Vector2D,
    private val dim: Vector2D,
    private val renderContext: RenderContext
)
