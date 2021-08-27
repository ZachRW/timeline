package timelinejs.config

import timelinejs.rendering.simple.style.TextStyle

data class DataConfig(
    val textConfig: TextStyle
) {
    companion object {
        val DEFAULT = DataConfig(
            textConfig = TextStyle(
                jsStyle = "black",
                font = "10px"
            )
        )
    }
}
