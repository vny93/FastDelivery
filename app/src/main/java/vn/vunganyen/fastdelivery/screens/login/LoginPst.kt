package vn.vunganyen.fastdelivery.screens.login

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiAuthService
import vn.vunganyen.fastdelivery.data.api.ApiStaffService
import vn.vunganyen.fastdelivery.data.model.login.LoginReq
import vn.vunganyen.fastdelivery.data.model.login.LoginRes
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class LoginPst {
    var loginItf : LoginItf
    var gson = Gson()

    constructor(loginItf: LoginItf) {
        this.loginItf = loginItf
    }

    fun checkEmpty(username: String, pw: String) {
        if (username.isEmpty() || pw.isEmpty()) {
            loginItf.loginEmpty()
            return
        }
        var req = LoginReq(username,pw)
        handle(req)
    }

    fun handle(req: LoginReq){
        ApiAuthService.Api.api.authLogin(req).enqueue(object : Callback<LoginRes>{
            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                if(response.isSuccessful){
                    SplashActivity.editor.putString("token",response.body()!!.accessToken)
                    SplashActivity.editor.putInt("roleId",response.body()!!.maquyen)
                    var token = response.body()!!.accessToken
                    var roleId = response.body()!!.maquyen
                    SplashActivity.roleId = roleId
                    if(roleId == SplashActivity.STORE){
                        //call api get profile store
                    }
                    else{
                        getProfile(token, ProfileReq(req.tendangnhap))
                    }

                }
                else{
                    loginItf.loginFail()
                }
            }

            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                Log.d("error" , ""+call)
                println("error "+call)
                t.printStackTrace()
            }

        })
    }

    fun getProfile(token : String, req: ProfileReq){
        ApiStaffService.Api.api.getProfile(token,req).enqueue(object : Callback<MainProfileRes>{
            override fun onResponse(call: Call<MainProfileRes>, response: Response<MainProfileRes>) {
                if(response.isSuccessful){
                    println("mã admin login: "+response.body()!!.result.manv)
                    setProfile(response.body()!!)
                    loginItf.loginSuccess()
                }
            }

            override fun onFailure(call: Call<MainProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getProfileStore(){
        //làm sau
    }

    fun setProfile(response : MainProfileRes){
        var strResponse = gson.toJson(response).toString()
        SplashActivity.editor.putString("profile",strResponse)
    }
}