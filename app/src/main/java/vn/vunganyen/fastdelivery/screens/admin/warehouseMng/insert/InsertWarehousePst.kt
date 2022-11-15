package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.insert

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistrictService
import vn.vunganyen.fastdelivery.data.api.ApiWarehouseService
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.data.model.warehouse.InsertWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.InsertWHRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class InsertWarehousePst {
    var insertWarehouseItf : InsertWarehouseItf

    constructor(insertWarehouseItf: InsertWarehouseItf) {
        this.insertWarehouseItf = insertWarehouseItf
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes> {
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    insertWarehouseItf.getListDistrict(response.body()!!.districts)
                }
            }

            override fun onFailure(call: Call<MainGetDistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getWards(code : Long){
        ApiDistrictService.Api.api.getDistrict2(code).enqueue(object : Callback<DistrictRes>{
            override fun onResponse(call: Call<DistrictRes>, response: Response<DistrictRes>) {
                if(response.isSuccessful){
                    insertWarehouseItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun constraintCheck(req : InsertWHReq) {
        if (req.tenkho.isEmpty() || req.diachi.isEmpty()) {
            insertWarehouseItf.Empty()
            return
        }
        addWarehouse(req)
    }

    fun addWarehouse(req : InsertWHReq){
        ApiWarehouseService.Api.api.insert(SplashActivity.token,req).enqueue(object : Callback<InsertWHRes>{
            override fun onResponse(call: Call<InsertWHRes>, response: Response<InsertWHRes>) {
                if(response.isSuccessful){
                    insertWarehouseItf.AddSucces()
                }
            }

            override fun onFailure(call: Call<InsertWHRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}