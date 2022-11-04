package vn.vunganyen.fastdelivery.data.model.district

data class DistrictRes(
    var name : String,
    var code : Long,
    var division_type : String,
    var codename : String,
    var province_code : Long,
    var wards : List<WardsRes>
)