package vn.vunganyen.fastdelivery.screens.staff.home

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import vn.vunganyen.fastdelivery.data.api.GraphhopperService
import vn.vunganyen.fastdelivery.data.model.graphhopper.GraphhopperRes
import vn.vunganyen.fastdelivery.data.model.graphhopper.PointReq
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class HomeStaffPst {
    var homeStaffItf : HomeStaffItf
    var gson = Gson()

    constructor(homeStaffItf: HomeStaffItf) {
        this.homeStaffItf = homeStaffItf
    }


    fun getProfileEditor(){
        var strResponse =  SplashActivity.sharedPreferences.getString("profile","")
        SplashActivity.profile = gson.fromJson(strResponse, MainProfileRes::class.java)
    }

}