package vn.vunganyen.fastdelivery.screens.shipper.home

import com.google.gson.Gson
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class HomeShipperPst {
    var homeShipperIft : HomeShipperItf
    var gson = Gson()

    constructor(homeShipperIft: HomeShipperItf) {
        this.homeShipperIft = homeShipperIft
    }

    fun getProfileEditor(){
        var strResponse =  SplashActivity.sharedPreferences.getString("profile","")
        SplashActivity.profile = gson.fromJson(strResponse, MainProfileRes::class.java)
    }

}