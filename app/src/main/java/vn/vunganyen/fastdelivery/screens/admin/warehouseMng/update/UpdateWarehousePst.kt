package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.update

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistrictService
import vn.vunganyen.fastdelivery.data.api.ApiWarehouseService
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.warehouse.InsertWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class UpdateWarehousePst {
    var updateWarehouseItf : UpdateWarehouseItf

    constructor(updateWarehouseItf: UpdateWarehouseItf) {
        this.updateWarehouseItf = updateWarehouseItf
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes> {
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    updateWarehouseItf.getListDistrict(response.body()!!.districts)
                }
            }

            override fun onFailure(call: Call<MainGetDistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getWards(code : Long){
        ApiDistrictService.Api.api.getDistrict2(code).enqueue(object : Callback<DistrictRes> {
            override fun onResponse(call: Call<DistrictRes>, response: Response<DistrictRes>) {
                if(response.isSuccessful){
                    updateWarehouseItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun constraintCheck(req : WarehouseRes) {
        if (req.tenkho.isEmpty() || req.diachi.isEmpty()) {
            updateWarehouseItf.Empty()
            return
        }
        updateWarehouse(req)
    }

    fun updateWarehouse(req : WarehouseRes){
        ApiWarehouseService.Api.api.update(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    updateWarehouseItf.UpdateSucces()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}