package vn.vunganyen.fastdelivery.screens.shop.changePassword

interface ShopChangePwItf {
    fun ErrorIsEmpty()
    fun RegexPassword()
    fun ErrorConfirmPw()
    fun ErrorCurrentPw()
    fun ChangePwSuccess()
    fun ChangePwFail()
}