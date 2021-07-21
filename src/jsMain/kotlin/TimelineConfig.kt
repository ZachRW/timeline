import org.w3c.dom.*

data class TextConfig(
    val text: String,
    val font: String,
    val fillStyle: String = "black",
    val strokeStyle: String = fillStyle
) {
    fun getDim(ctx: CanvasRenderingContext2D): Vector2D {
        ctx.font = font
        return with(ctx.measureText(text)) {
            Vector2D(width, height)
        }
    }

    fun setFontAndStyle(ctx: CanvasRenderingContext2D) {
        ctx.textBaseline = CanvasTextBaseline.Companion.TOP
        ctx.textAlign = CanvasTextAlign.Companion.CENTER
        ctx.font = font
        ctx.fillStyle = fillStyle
        ctx.strokeStyle = strokeStyle
    }
}
