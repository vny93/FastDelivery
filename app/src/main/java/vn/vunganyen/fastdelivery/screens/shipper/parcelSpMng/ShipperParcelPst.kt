package vn.vunganyen.fastdelivery.screens.shipper.parcelSpMng

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistrictService
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.api.ApiStatusService
import vn.vunganyen.fastdelivery.data.api.ApiWayService
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusReq
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusRes
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.parcel.MainSpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.UpdatePaymentStatusReq
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.MainGetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.CheckWayExistReq
import vn.vunganyen.fastdelivery.data.model.way.UpdateWayReq
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ShipperParcelPst {
    var shipperParcelItf : ShipperParcelItf

    constructor(shipperParcelItf: ShipperParcelItf) {
        this.shipperParcelItf = shipperParcelItf
    }

    fun getListStatus(){
        ApiStatusService.Api.api.getListStatus(SplashActivity.token).enqueue(object :
            Callback<MainListStatusRes> {
            override fun onResponse(call: Call<MainListStatusRes>, response: Response<MainListStatusRes>) {
                if(response.isSuccessful){
                    shipperParcelItf.getListStatus(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainListStatusRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes>{
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    shipperParcelItf.getListDistrict(response.body()!!.districts)
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
                    shipperParcelItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun filterParcel(req : SpGetParcelReq){
        println("vô")
        ApiParcelService.Api.api.shipper_get_parcel(SplashActivity.token,req).enqueue(object : Callback<MainSpGetParcelRes>{
            override fun onResponse(call: Call<MainSpGetParcelRes>, response: Response<MainSpGetParcelRes>) {
                if(response.isSuccessful){
                    println("????")
                    shipperParcelItf.getListParcel(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainSpGetParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getIdStatus(req : GetIdStatusReq, req2 : SpGetParcelRes){
        ApiStatusService.Api.api.getIdStatus(SplashActivity.token,req).enqueue(object : Callback<MainGetIdStatusRes>{
            override fun onResponse(call: Call<MainGetIdStatusRes>, response: Response<MainGetIdStatusRes>) {
                if(response.isSuccessful){
                    println("Vô r nè")
                    shipperParcelItf.getIdStatus(response.body()!!.result.get(0),req2)
                }
            }

            override fun onFailure(call: Call<MainGetIdStatusRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addDetailStatus(req : DetailStatusReq){
        ApiStatusService.Api.api.addDetailStatus(SplashActivity.token,req).enqueue(object : Callback<DetailStatusRes>{
            override fun onResponse(call: Call<DetailStatusRes>, response: Response<DetailStatusRes>) {
                if(response.isSuccessful){
                    shipperParcelItf.addDetailStatus()
                }
            }

            override fun onFailure(call: Call<DetailStatusRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkWayExist(req : CheckWayExistReq, data : SpGetParcelRes){
        println("Check trạng thái là: "+req.matt)
        ApiWayService.Api.api.checkWayExist(SplashActivity.token,req).enqueue(object : Callback<MainWarehouseRes>{
            override fun onResponse(call: Call<MainWarehouseRes>, response: Response<MainWarehouseRes>) {
                if(response.isSuccessful) {
                    if(req.matt == 0){ // nếu tìm trạng thái 0 thì update thành 1
                        println("Có trạng thái 0 nè")
                        shipperParcelItf.wayExist(response.body()!!.result.get(0), data)
                    }
                    else{
                        // nếu tìm trạng thái 1 thì update thành 2
                        println("Có trạng thái 1 nè")
                        var idWarehouse = response.body()!!.result.get(0).makho
                        var req = UpdateWayReq(2,data.mabk,idWarehouse)
                        updateWay(req,data)
                    }
                }
                else{
                    shipperParcelItf.wayNotExist(data)
                }
            }
            override fun onFailure(call: Call<MainWarehouseRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateWay(req : UpdateWayReq, req3 : SpGetParcelRes){
        println("matt: "+req.trangthai)
        ApiWayService.Api.api.update(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    if(req.trangthai == 1){
                        shipperParcelItf.updateWay(req3)
                    }
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updatePaymentStatus(req : UpdatePaymentStatusReq, req2: SpGetParcelRes){
        ApiParcelService.Api.api.update_payment_status(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    shipperParcelItf.updatePaymentStatus(req2)
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}