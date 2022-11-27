package vn.vunganyen.fastdelivery.screens.forgotPassword

import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq

interface ForgotPasswordItf {
    fun AccountNotExist()
    fun AccountExist(req : ProfileReq)
    fun Empty()
    fun EmailIllegal()
    fun sendSuccess()
}