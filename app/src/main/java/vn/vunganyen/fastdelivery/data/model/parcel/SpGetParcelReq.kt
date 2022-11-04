package vn.vunganyen.fastdelivery.data.model.parcel

data class SpGetParcelReq(
    val dateFrom : String,
    val dateTo : String,
    val matt : Int,
    val idshipper : String
)