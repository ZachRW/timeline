package timelinejs

data class SeriesColorPalette(
    private val colors: List<String>
) : List<String> by colors {
    companion object {
        val DEFAULT = SeriesColorPalette(
            listOf(
                "#636EFA",
                "#EF553B",
                "#00CC96",
                "#AB63FA",
                "#FFA15A",
                "#19D3F3",
                "#FF6692",
                "#B6E880",
                "#FF97FF",
                "#FECB52"
            )
        )
    }
}

class SeriesColorProvider(private val palette: SeriesColorPalette) {
    private var index = 0
        set(value) {
            field = value % palette.size
        }

    fun next() = palette[index++]
}
