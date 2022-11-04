package vn.vunganyen.fastdelivery.data.model.auth

data class AuthRegisterReq(
    var tendangnhap : String,
    var matkhau : String,
    var maquyen : Int
)