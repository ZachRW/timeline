package timelinejs.config

data class DataConfig(
    val textConfig: TextConfig
) {
    companion object {
        val DEFAULT = DataConfig(
            textConfig = TextConfig(
                color = "black",
                font = "10px"
            )
        )
    }
}
