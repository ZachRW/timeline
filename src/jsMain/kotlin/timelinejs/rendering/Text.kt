package timelinejs.rendering

class Text(
    private val x: Double,
    private val y: Double,
    private val text: String,
    private val style: TextStyle,
    private val renderer: Renderer
) : Renderable {
    val bounds: Rectangle by lazy {
        applyConfig()
        renderer.textBounds(text)
    }

    override fun render() {
        applyConfig()
        fillOrStroke()
    }

    private fun applyConfig() {
        with(renderer) {
            font = style.font
            textAlign = style.textAlign
            textBaseline = style.textBaseline

            when (style.drawMode) {
                DrawMode.FILL -> fillStyle = style.style
                DrawMode.STROKE -> strokeStyle = style.style
            }
        }
    }

    private fun fillOrStroke() {
        when (style.drawMode) {
            DrawMode.FILL -> renderer.fillText(text, x, y)
            DrawMode.STROKE -> renderer.strokeText(text, x, y)
        }
    }
}
