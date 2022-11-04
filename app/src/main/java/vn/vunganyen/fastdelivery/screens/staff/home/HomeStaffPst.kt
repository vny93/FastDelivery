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

    fun callAPIGraphhopperRes(){
        var pointSource = "10.8684163,106.65234149999999"
        var pointDes = "10.8276003,106.7212132"
        var profile = "car"
        var locale = "vn"
        var calc_points = false
        var key = "03b225ae-3ff7-40d9-86ee-901bc4347172"
        println("hihihi")
        GraphhopperService.Api.api.getDistanceApi(
            pointSource,pointDes,profile,locale, calc_points, key).enqueue(object : Callback<GraphhopperRes>{
            override fun onResponse(call: Call<GraphhopperRes>, response: Response<GraphhopperRes>) {
                println("--------API:"+response.raw().request.url)
                if(response.isSuccessful){
                    println("distance: "+response.body()!!.paths!!.get(0).distance)
                }
            }

            override fun onFailure(call: Call<GraphhopperRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}