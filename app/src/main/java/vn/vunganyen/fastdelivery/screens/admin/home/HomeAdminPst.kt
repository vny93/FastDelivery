package vn.vunganyen.petshop.screens.admin.main

import com.google.gson.Gson
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class HomeAdminPst {
    var mainAdminInterface : HomeAdminItf
    var gson = Gson()

    constructor(mainAdminInterface: HomeAdminItf) {
        this.mainAdminInterface = mainAdminInterface
    }

    fun getProfileEditor(){
        var strResponse =  SplashActivity.sharedPreferences.getString("profile","")
        SplashActivity.profile = gson.fromJson(strResponse, MainProfileRes::class.java)
    }

}