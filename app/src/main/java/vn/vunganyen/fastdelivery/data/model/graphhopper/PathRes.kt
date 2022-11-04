package vn.vunganyen.fastdelivery.data.model.graphhopper

data class PathRes(
    val distance: Double? = null,
    val weight: Double? = null,
    val time: Long? = null,
    val transfers: Long? = null,
    val snappedWaypoints: String? = null
)