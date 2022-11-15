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
import vn.vunganyen.fastdelivery.data.model.parcel.MainStGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.MainGetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.data.model.way.CheckWayExistReq
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
        ApiParcelService.Api.api.shipper_get_parcel(SplashActivity.token,req).enqueue(object : Callback<MainStGetParcelRes>{
            override fun onResponse(call: Call<MainStGetParcelRes>, response: Response<MainStGetParcelRes>) {
                if(response.isSuccessful){
                    println("????")
                    shipperParcelItf.getListParcel(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainStGetParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getIdStatus(req : GetIdStatusReq, req2 : StGetParcelRes){
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

    fun checkWayExist(req : CheckWayExistReq, data : StGetParcelRes){
        ApiWayService.Api.api.checkWayExist(SplashActivity.token,req).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    shipperParcelItf.checkWayExist(response.body()!!.result, data)
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}