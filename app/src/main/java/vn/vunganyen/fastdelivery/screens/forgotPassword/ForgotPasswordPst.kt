package vn.vunganyen.fastdelivery.screens.forgotPassword

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiAuthService
import vn.vunganyen.fastdelivery.data.api.ApiMailService
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.mail.MailReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ForgotPasswordPst {
    var forgotPasswordItf : ForgotPasswordItf

    constructor(forgotPasswordItf: ForgotPasswordItf) {
        this.forgotPasswordItf = forgotPasswordItf
    }

    fun checkUser(req : ProfileReq){
        if (req.tendangnhap.isEmpty()){
            forgotPasswordItf.Empty()
            return
        }
        findAccount(req)
    }

    fun checkEmail(req : MailReq){
        if (req.email.isEmpty()){
            forgotPasswordItf.Empty()
            return
        }
        if (!SplashActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            forgotPasswordItf.EmailIllegal()
            return
        }
        sendNewPassword(req)
    }

    fun findAccount(req : ProfileReq){
        ApiAuthService.Api.api.findAuth(req).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == true){
                        forgotPasswordItf.AccountExist(req)
                    }
                    else forgotPasswordItf.AccountNotExist()
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun sendNewPassword(req : MailReq){
        ApiMailService.Api.api.sendMail(req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    forgotPasswordItf.sendSuccess()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}