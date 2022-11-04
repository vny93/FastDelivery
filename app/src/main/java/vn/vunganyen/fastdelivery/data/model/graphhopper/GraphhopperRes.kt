package vn.vunganyen.fastdelivery.data.model.graphhopper

data class GraphhopperRes (
    val hints: HintsRes? = null,
    val info: InforRes? = null,
    val paths: List<PathRes>? = null
)