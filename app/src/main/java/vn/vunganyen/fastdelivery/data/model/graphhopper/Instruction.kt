package vn.vunganyen.fastdelivery.data.model.graphhopper

data class Instruction(
    val distance: Double,
    val heading: Double? = null,
    val sign: Long,
    val interval: List<Long>,
    val text: String,
    val time: Long,
    val streetName: String,
    val exitNumber: Long? = null,
    val exited: Boolean? = null,
    val turnAngle: Double? = null,
    val lastHeading: Double? = null
)