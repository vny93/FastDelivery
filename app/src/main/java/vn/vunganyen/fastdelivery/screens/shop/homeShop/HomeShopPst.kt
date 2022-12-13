package vn.vunganyen.fastdelivery.screens.shop.homeShop

import com.google.gson.Gson
import vn.vunganyen.fastdelivery.data.model.shop.MainShopProfileRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class HomeShopPst {
    var gson = Gson()

    fun getProfileEditor(){
        var strResponse =  SplashActivity.sharedPreferences.getString("profileShop","")
        SplashActivity.profileShop = gson.fromJson(strResponse, MainShopProfileRes::class.java)
    }
}