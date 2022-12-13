package vn.vunganyen.fastdelivery.data.model.shop

import java.io.Serializable

data class GetShopDetailRes(
    var mach : String,
    var tench : String,
    var sdt : String,
    var email : String,
    var diachi : String,
    var tendangnhap : String,
    var trangthai : Int
):Serializable