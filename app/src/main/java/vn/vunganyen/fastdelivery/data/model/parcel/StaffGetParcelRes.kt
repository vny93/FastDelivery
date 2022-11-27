package vn.vunganyen.fastdelivery.data.model.parcel

import java.io.Serializable

data class StaffGetParcelRes(
    val mabk : Int,
    val hotennguoinhan : String,
    val sdtnguoinhan: String,
    val diachinguoinhan : String,
    val ptthanhtoan : String,
    val sotien : Float,
    val tinhtrangthanhtoan : String,
    val khoiluong : Float,
    val kichthuoc : String,
    val phigiao : Float,
    val htvanchuyen : String,
    val ghichu : String,
    val ngaygui : String,
    val mach : String,
    var idcheck : Int,
    val thoigian : String,
    val tentrangthai : String,
    val manv : String,
    val mashipper : String
):Serializable