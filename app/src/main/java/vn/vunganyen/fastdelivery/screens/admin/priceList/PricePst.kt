package vn.vunganyen.fastdelivery.screens.admin.priceList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistanceService
import vn.vunganyen.fastdelivery.data.api.ApiMassService
import vn.vunganyen.fastdelivery.data.model.distance.*
import vn.vunganyen.fastdelivery.data.model.mass.*
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

    fun checkMaxMass(req : AddMassReq){
        ApiMassService.Api.api.getMaxMass(SplashActivity.token).enqueue(object : Callback<MainGetMaxRes>{
            override fun onResponse(call: Call<MainGetMaxRes>, response: Response<MainGetMaxRes>) {
                if(response.isSuccessful){
                    var max = response.body()!!.result.get(0).max
                    println("max:"+max)
                    if(req.klbatdau >= max){
                        comparetoMass(req)
                    }
                    else{
                        priceItf.massError(max)
                    }
                }
            }

            override fun onFailure(call: Call<MainGetMaxRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkMaxDistance(req : AddDistanceReq){
        ApiDistanceService.Api.api.getMaxDistance(SplashActivity.token).enqueue(object : Callback<MainGetMaxRes>{
            override fun onResponse(call: Call<MainGetMaxRes>, response: Response<MainGetMaxRes>) {
                if(response.isSuccessful){
                    var max = response.body()!!.result.get(0).max
                    println("max:"+max)
                    if(req.kcbatdau >= max){
                        comparetoDistance(req)
                    }
                    else{
                        priceItf.distanceErorr(max)
                    }
                }
            }

            override fun onFailure(call: Call<MainGetMaxRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun comparetoMass(req : AddMassReq){
        val result = req.klketthuc.compareTo(req.klbatdau)
        if(result < 0 || result == 0){
            priceItf.comtopareMassError()
            return
        }
        if(req.giatien.toInt() == 0){
            priceItf.priceError()
            return
        }
        addMass(req)
    }

    fun comparetoDistance(req : AddDistanceReq){
        val result = req.kcketthuc.compareTo(req.kcbatdau)
        if(result < 0 || result == 0){
            priceItf.comtopareDistanceError()
            return
        }
        if(req.giatien.toInt() == 0){
            priceItf.priceError()
            return
        }
        addDistance(req)
    }

    fun deleteMass(req : DeleteMassReq){
        ApiMassService.Api.api.deleteMass(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    priceItf.deleteMass()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteDistance(req : DeleteDistanceReq){
        ApiDistanceService.Api.api.deleteDistance(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    priceItf.deleteDistance()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addDistance(req : AddDistanceReq){
        ApiDistanceService.Api.api.addDistance(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    priceItf.addDistance()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addMass(req : AddMassReq){
        ApiMassService.Api.api.addMass(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    priceItf.addMass()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}