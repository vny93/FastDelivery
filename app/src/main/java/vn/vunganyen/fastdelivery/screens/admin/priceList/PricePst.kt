package vn.vunganyen.fastdelivery.screens.admin.priceList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistanceService
import vn.vunganyen.fastdelivery.data.api.ApiMassService
import vn.vunganyen.fastdelivery.data.model.distance.DistanceUPriceReq
import vn.vunganyen.fastdelivery.data.model.distance.MainDistanceRes
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class PricePst {
    var priceItf : PriceItf

    constructor(priceItf: PriceItf) {
        this.priceItf = priceItf
    }

    fun getListDistance(){
        ApiDistanceService.Api.api.getList(SplashActivity.token).enqueue(object : Callback<MainDistanceRes>{
            override fun onResponse(call: Call<MainDistanceRes>, response: Response<MainDistanceRes>) {
                if(response.isSuccessful){
                    priceItf.getListDistance(response.body()!!.result)
                    getListMass()
                }
            }

            override fun onFailure(call: Call<MainDistanceRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListMass(){
        ApiMassService.Api.api.getList(SplashActivity.token).enqueue(object : Callback<MainMassRes>{
            override fun onResponse(call: Call<MainMassRes>, response: Response<MainMassRes>) {
                if(response.isSuccessful){
                    priceItf.getListMass(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainMassRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateMassPrice(req : MassUPriceReq){
        ApiMassService.Api.api.updateMassPrice(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    priceItf.updateMassPrice()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateDistancePrice(req : DistanceUPriceReq){
        ApiDistanceService.Api.api.updateDistancePrice(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    priceItf.updateDistancePrice()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}