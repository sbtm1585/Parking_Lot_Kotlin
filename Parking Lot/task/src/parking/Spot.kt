package parking

data class Spot(
    val num: Int,
    var reg: String = "",
    var color: String = "",
    var vacant: Boolean = true
)
