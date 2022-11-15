package vn.vunganyen.fastdelivery.data.model.staff

import java.io.Serializable

data class ListStaffRes(
    var manv : String,
    var cmnd : String,
    var hoten : String,
    var gioitinh : String,
    var ngaysinh : String,
    var sdt : String,
    var email : String,
    var diachi : String,
    var makho : Int,
    var tendangnhap : String,
    var matkhau : String,
    var trangthai : Int,
    var maquyen : Int,
    var tenquyen : String
):Serializable