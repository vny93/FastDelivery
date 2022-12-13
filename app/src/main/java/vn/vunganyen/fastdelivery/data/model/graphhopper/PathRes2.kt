package vn.vunganyen.fastdelivery.data.model.graphhopper

data class PathRes2(
    val distance: Double,
    val weight: Double,
    val time: Long,
    val transfers: Long,
    val pointsEncoded: Boolean,
    val bbox: List<Double>,
    val points: String,
    val instructions: List<Instruction>,
    val legs: List<Any?>,
    val details: Details,
    val ascend: Double,
    val descend: Double,
    val snappedWaypoints: String
)