package timelinejs.rendering

class RoundRectangle(
    private val bounds: Rectangle,
    private val style: RoundRectangleStyle,
    private val renderer: Renderer
) : Renderable {
    override fun render() {
        applyConfig()
        fillOrStroke()
    }

    private fun applyConfig() {
        with(renderer) {
            lineWidth = style.lineWidth
            lineDash = style.lineDash

            when (style.drawMode) {
                DrawMode.FILL -> fillStyle = style.style
                DrawMode.STROKE -> strokeStyle = style.style
            }
        }
    }

    private fun fillOrStroke() {
        val drawFun = renderer.getDrawFun(style.drawMode)
        drawFun {
            roundRect(bounds, style.radius)
        }
    }
}