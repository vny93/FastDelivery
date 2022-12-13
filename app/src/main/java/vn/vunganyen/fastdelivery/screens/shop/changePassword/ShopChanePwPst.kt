package vn.vunganyen.fastdelivery.screens.shop.changePassword

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiAuthService
import vn.vunganyen.fastdelivery.data.model.auth.ChangePwReq
import vn.vunganyen.fastdelivery.data.model.classSupport.MD5Hash
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ShopChanePwPst {
    var shopChangePwItf : ShopChangePwItf
    var tendangnhap = SplashActivity.profileShop.result.tendangnhap
    var md5 : MD5Hash = MD5Hash()

    constructor(shopChangePwItf: ShopChangePwItf) {
        this.shopChangePwItf = shopChangePwItf
    }

    fun validCheck(curentPw: String, pw1: String, pw2: String) {
        println("curentPw: "+curentPw)
        println("pw1: "+pw1)
        println("pw2: "+pw2)
        if (curentPw.isEmpty() || pw1.isEmpty() || pw2.isEmpty()) {
            shopChangePwItf.ErrorIsEmpty()
            return
        }
        if (SplashActivity.PASSWORD.matcher(pw1).matches() && SplashActivity.PASSWORD.matcher(pw2).matches())
        {
            if (pw1.equals(pw2)) {
                checkAccount(curentPw, pw1)
            } else shopChangePwItf.ErrorConfirmPw()
        } else shopChangePwItf.RegexPassword()
    }

    fun checkAccount(curentPw: String, pw1: String){
        var req = ChangePwReq(tendangnhap,curentPw)
        println("kaka")
        ApiAuthService.Api.api.checkAuth(SplashActivity.token,req).enqueue(object :
            Callback<CheckWordRes> {
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result == true){
                        println("vô nè")
                        changePassword(md5.md5Code(pw1))
                    }
                    else shopChangePwItf.ErrorCurrentPw()
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun changePassword(newPassword : String){
        var req = ChangePwReq(tendangnhap,newPassword)
        println("haha")
        ApiAuthService.Api.api.changePassword(SplashActivity.token,req).enqueue(object :
            Callback<UpdateRes> {
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    println("hihi")
                    shopChangePwItf.ChangePwSuccess()
                }
                else shopChangePwItf.ChangePwFail()
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}