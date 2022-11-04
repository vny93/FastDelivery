package vn.vunganyen.fastdelivery.data.model.staff

data class InsertShipperReq(
    var manv: String,
    var cmnd : String,
    var hoten: String,
    var gioitinh: String,
    var ngaysinh: String,
    var sdt: String,
    var email: String,
    var diachi: String,
    var tendangnhap: String
)