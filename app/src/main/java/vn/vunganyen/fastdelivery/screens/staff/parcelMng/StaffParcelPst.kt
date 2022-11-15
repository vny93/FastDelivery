package vn.vunganyen.fastdelivery.screens.staff.parcelMng

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.*
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusReq
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusRes
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.parcel.*
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.MainGetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.staff.MainShipperAreaRes
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaReq
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.MainGetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class StaffParcelPst {
    var staffParcelItf : StaffParcelItf

    constructor(staffParcelItf: StaffParcelItf) {
        this.staffParcelItf = staffParcelItf
    }

    fun getListStatus(){
        ApiStatusService.Api.api.getListStatus(SplashActivity.token).enqueue(object :
            Callback<MainListStatusRes> {
            override fun onResponse(call: Call<MainListStatusRes>, response: Response<MainListStatusRes>) {
                if(response.isSuccessful){
                    staffParcelItf.getListStatus(response.body()!!.result)
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
                    staffParcelItf.getListDistrict(response.body()!!.districts)
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
                    staffParcelItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun filterParcel(req : StGetParcelReq){
        println("trangthai: "+req.trangthai)
        ApiParcelService.Api.api.staff_get_parcel(SplashActivity.token,req).enqueue(object : Callback<MainStGetParcelRes>{
            override fun onResponse(call: Call<MainStGetParcelRes>, response: Response<MainStGetParcelRes>) {
                println("????")
                if(response.isSuccessful){
                    staffParcelItf.getListParcel(response.body()!!.result)
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
                    staffParcelItf.getIdStatus(response.body()!!.result.get(0),req2)
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
                    staffParcelItf.addDetailStatus()
                }
            }

            override fun onFailure(call: Call<DetailStatusRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getShopDetail(req : GetShopDetailReq){
        ApiShopService.Api.api.getShopDetail(SplashActivity.token,req).enqueue(object : Callback<MainGetShopDetailRes>{
            override fun onResponse(call: Call<MainGetShopDetailRes>, response: Response<MainGetShopDetailRes>) {
                if(response.isSuccessful){
                    staffParcelItf.getShopDetail(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetShopDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getShipperArea(req : ShipperAreaReq){
        ApiStaffService.Api.api.getShipperArea(SplashActivity.token,req).enqueue(object : Callback<MainShipperAreaRes>{
            override fun onResponse(call: Call<MainShipperAreaRes>, response: Response<MainShipperAreaRes>) {
                if(response.isSuccessful){
                    staffParcelItf.getShipperArea(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainShipperAreaRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}