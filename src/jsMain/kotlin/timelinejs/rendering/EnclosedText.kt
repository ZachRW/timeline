package timelinejs.rendering

class EnclosedText(
    x: Double,
    y: Double,
    textStr: String,
    roundRectStyle: RoundRectangleStyle,
    textStyle: TextStyle,
    renderer: Renderer
) : Renderable {
    private val roundRect: RoundRectangle
    private val text: Text

    init {
        val text = Text(x, y, textStr, textStyle, renderer)
    }

    override fun render() {
        roundRect.render()
        text.render()
    }
}