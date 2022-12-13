package vn.vunganyen.fastdelivery.screens.shipper.registerArea

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiAreaService
import vn.vunganyen.fastdelivery.data.api.ApiDetailRegisterService
import vn.vunganyen.fastdelivery.data.model.area.MainGetListAreaRes
import vn.vunganyen.fastdelivery.data.model.detailRegister.GetAreaShipperReq
import vn.vunganyen.fastdelivery.data.model.detailRegister.InsertAreaReq
import vn.vunganyen.fastdelivery.data.model.detailRegister.InsertAreaRes
import vn.vunganyen.fastdelivery.data.model.detailRegister.UpdateAreaShipperReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class RegisterAreaPst {
    var registerAreaItf : RegisterAreaItf

    constructor(registerAreaItf: RegisterAreaItf) {
        this.registerAreaItf = registerAreaItf
    }

    fun getListRegisterArea(req : GetAreaShipperReq){
        ApiDetailRegisterService.Api.api.getAreaShipper(SplashActivity.token,req).enqueue(object : Callback<MainGetListAreaRes>{
            override fun onResponse(call: Call<MainGetListAreaRes>, response: Response<MainGetListAreaRes>) {
                if(response.isSuccessful){
                    registerAreaItf.getListRegisterArea(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetListAreaRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListArea(){
        ApiAreaService.Api.api.getListArea(SplashActivity.token).enqueue(object : Callback<MainGetListAreaRes>{
            override fun onResponse(call: Call<MainGetListAreaRes>, response: Response<MainGetListAreaRes>) {
                if(response.isSuccessful){
                    registerAreaItf.getListArea(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetListAreaRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkRegisterExist(req : InsertAreaReq){
        ApiDetailRegisterService.Api.api.checkAreaShipper(SplashActivity.token,req).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == false){
                        addAreaShipper(req)
                    }
                    else{
                        registerAreaItf.registerExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkEmpty(req : InsertAreaReq){
        if(req.makhuvuc == 0 ){
            registerAreaItf.Empty()
            return
        }
        checkRegisterExist(req)
    }

    fun addAreaShipper(req : InsertAreaReq){
        ApiDetailRegisterService.Api.api.addAreaShipper(SplashActivity.token,req).enqueue(object : Callback<InsertAreaRes>{
            override fun onResponse(call: Call<InsertAreaRes>, response: Response<InsertAreaRes>) {
                if(response.isSuccessful){
                    registerAreaItf.addSuccess()
                }
            }

            override fun onFailure(call: Call<InsertAreaRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateArea(req : UpdateAreaShipperReq){
        ApiDetailRegisterService.Api.api.updateAreaShipper(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    registerAreaItf.updateSuccess()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}