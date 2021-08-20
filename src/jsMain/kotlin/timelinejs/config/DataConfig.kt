package timelinejs.config

import timelinejs.rendering.TextStyle

data class DataConfig(
    val textConfig: TextStyle
) {
    companion object {
        val DEFAULT = DataConfig(
            textConfig = TextStyle(
                color = "black",
                font = "10px"
            )
        )
    }
}
