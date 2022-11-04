package vn.vunganyen.fastdelivery.data.model.parcel

data class StGetParcelReq(
    val dateFrom : String,
    val dateTo : String,
    val matt : Int,
    val makho : Int
)