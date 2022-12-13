package vn.vunganyen.fastdelivery.screens.staff.parcelMng

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.*
import vn.vunganyen.fastdelivery.data.model.detailStatus.CountReq
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusReq
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusRes
import vn.vunganyen.fastdelivery.data.model.detailStatus.MainCountRes
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
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.CheckWayExistReq
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
        ApiParcelService.Api.api.staff_get_parcel(SplashActivity.token,req).enqueue(object : Callback<MainStaffParcelRes>{
            override fun onResponse(call: Call<MainStaffParcelRes>, response: Response<MainStaffParcelRes>) {
                println("????")
                if(response.isSuccessful){
                    StaffParceFgm.listParcel = response.body()!!.result as ArrayList<StaffGetParcelRes>
                    staffParcelItf.getListParcel(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainStaffParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getIdStatus(req : GetIdStatusReq, req2 : StaffGetParcelRes){
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

    fun getWarehouseDetail(req : GetDetailWHReq){
        ApiWarehouseService.Api.api.getDetail(SplashActivity.token,req).enqueue(object : Callback<GetDetailWHRes>{
            override fun onResponse(call: Call<GetDetailWHRes>, response: Response<GetDetailWHRes>) {
                if(response.isSuccessful){
                    staffParcelItf.getWarehouseDetail(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<GetDetailWHRes>, t: Throwable) {
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
                    staffParcelItf.getShipperArea(response.body()!!.result, req.tentrangthai)
                }
            }

            override fun onFailure(call: Call<MainShipperAreaRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkWayExist(req : CheckWayExistReq, data : StaffGetParcelRes){
        ApiWayService.Api.api.checkWayExist(SplashActivity.token,req).enqueue(object : Callback<MainWarehouseRes>{
            override fun onResponse(call: Call<MainWarehouseRes>, response: Response<MainWarehouseRes>) {
                if(response.isSuccessful){
                    staffParcelItf.wayExist(response.body()!!.result.get(0), data)
                }
                else{
                    staffParcelItf.wayNotExist(data)
                }
            }

            override fun onFailure(call: Call<MainWarehouseRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun countDeliveredFail(data : StaffGetParcelRes){
        var req = CountReq(data.mabk)
        ApiStatusService.Api.api.count(SplashActivity.token,req).enqueue(object : Callback<MainCountRes>{
            override fun onResponse(call: Call<MainCountRes>, response: Response<MainCountRes>) {
                if(response.isSuccessful){
                    var res = response.body()!!.result.get(0)
                    staffParcelItf.count(res,data)
                }
            }

            override fun onFailure(call: Call<MainCountRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getFilter(s: String){
        StaffParceFgm.listFilter = ArrayList<StaffGetParcelRes>()
        for(list in StaffParceFgm.listParcel){
            if(list.mabk.toString().toUpperCase().contains(s.toUpperCase())){
                StaffParceFgm.listFilter.add(list)
            }
        }
        staffParcelItf.getListParcel(StaffParceFgm.listFilter)
    }

    //hàm mới, để refesh lọc lại với id tìm kiếm
    fun filterParcel2(req : StGetParcelReq, s : String){
        println("trangthai: "+req.trangthai)
        ApiParcelService.Api.api.staff_get_parcel(SplashActivity.token,req).enqueue(object : Callback<MainStaffParcelRes>{
            override fun onResponse(call: Call<MainStaffParcelRes>, response: Response<MainStaffParcelRes>) {
                println("????")
                if(response.isSuccessful){
                    StaffParceFgm.listParcel = response.body()!!.result as ArrayList<StaffGetParcelRes>
                    getFilter2(s)
                }
            }

            override fun onFailure(call: Call<MainStaffParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getFilter2(s: String){
        println("lọc")
        StaffParceFgm.listFilter = ArrayList<StaffGetParcelRes>()
        for(list in StaffParceFgm.listParcel){
            if(list.mabk.toString().equals(s)){
                StaffParceFgm.listFilter.add(list)
            }
        }
        staffParcelItf.getListParcel(StaffParceFgm.listFilter)
    }

    fun getCancelInfor(id : Int){
        ApiParcelService.Api.api.get_cancel_infor(SplashActivity.token, FullStatusDetailReq(id)).enqueue(object : Callback<MainCancelInforRes>{
            override fun onResponse(call: Call<MainCancelInforRes>, response: Response<MainCancelInforRes>) {
                if(response.isSuccessful){
                    staffParcelItf.cancelInfor(response.body()!!.result.get(0))
                }
            }

            override fun onFailure(call: Call<MainCancelInforRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}