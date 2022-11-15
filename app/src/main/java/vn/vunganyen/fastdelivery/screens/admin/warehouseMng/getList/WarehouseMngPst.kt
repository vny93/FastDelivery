package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.getList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiWarehouseService
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.data.model.warehouse.CheckWHUseRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class WarehouseMngPst {
    var warehouseMngItf : WarehouseMngItf

    constructor(warehouseMngItf: WarehouseMngItf) {
        this.warehouseMngItf = warehouseMngItf
    }

    fun getListWarehouse(){
        ApiWarehouseService.Api.api.getListWarehouse(SplashActivity.token).enqueue(object : Callback<MainWarehouseRes>{
            override fun onResponse(call: Call<MainWarehouseRes>, response: Response<MainWarehouseRes>) {
                if(response.isSuccessful){
                    WarehouseMngActivity.listWarehouse = response.body()!!.result as ArrayList<WarehouseRes>
                    warehouseMngItf.getListWarehouse(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainWarehouseRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkWarehouseUse(req : GetDetailWHReq){
        ApiWarehouseService.Api.api.checkWHUser(SplashActivity.token,req).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result == true){
                        warehouseMngItf.deleteFail()
                    }
                    else warehouseMngItf.allowDetele(req)
                }
            }
            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun deleteWarehouse(req : GetDetailWHReq ){
        ApiWarehouseService.Api.api.delete(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    warehouseMngItf.deleteSuccess()
                }
            }
            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getFilter(s : String){
        WarehouseMngActivity.listFilter = ArrayList<WarehouseRes>()
        for(list in WarehouseMngActivity.listWarehouse){
            if(list.makho.toString().contains(s.toUpperCase())){
                WarehouseMngActivity.listFilter.add(list)
            }
        }
        warehouseMngItf.getListWarehouse(WarehouseMngActivity.listFilter)
    }
}