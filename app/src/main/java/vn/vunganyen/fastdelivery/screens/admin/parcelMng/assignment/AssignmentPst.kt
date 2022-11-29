package vn.vunganyen.fastdelivery.screens.admin.parcelMng.assignment

import android.location.Address
import android.location.Geocoder
import android.location.Location
import demo.kotlin.model.dijkstra.Vert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.*
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.graphhopper.GraphhopperRes
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.MainAdGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.StaffGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.MainGetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhReq
import vn.vunganyen.fastdelivery.data.model.warehouse.MainGetParcelWhRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.WayReq
import vn.vunganyen.fastdelivery.data.model.way.WayRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.parcelMng.StaffParceFgm
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class AssignmentPst {
    var assignmentItf : AssignmentItf

    constructor(assignmentItf: AssignmentItf) {
        this.assignmentItf = assignmentItf
    }

    fun getListStatus(){
        ApiStatusService.Api.api.getListStatus(SplashActivity.token).enqueue(object : Callback<MainListStatusRes>{
            override fun onResponse(call: Call<MainListStatusRes>, response: Response<MainListStatusRes>) {
                if(response.isSuccessful){
                    assignmentItf.getListStatus(response.body()!!.result)
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
                    assignmentItf.getListDistrict(response.body()!!.districts)
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
                    assignmentItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun filterParcel(req : AdGetParcelReq){
        println("trangthai: "+req.trangthai)
        ApiParcelService.Api.api.admin_get_parcel(SplashActivity.token,req).enqueue(object : Callback<MainAdGetParcelRes>{
            override fun onResponse(call: Call<MainAdGetParcelRes>, response: Response<MainAdGetParcelRes>) {
                println("????")
                if(response.isSuccessful){
                    AssignmentMngActivity.listParcel = response.body()!!.result as ArrayList<AdGetParcelRes>
                    assignmentItf.getListParcel(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainAdGetParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun getListWarehouse(){
        ApiWarehouseService.Api.api.getListWarehouse(SplashActivity.token).enqueue(object : Callback<MainWarehouseRes>{
            override fun onResponse(call: Call<MainWarehouseRes>, response: Response<MainWarehouseRes>) {
                if(response.isSuccessful){
                    AssignmentMngActivity.listWarehouse = response.body()!!.result
                    var size = response.body()!!.result.size
                    println("size: "+size)
                    assignmentItf.getListWarehouse()
                }
            }

            override fun onFailure(call: Call<MainWarehouseRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getShopDetail(req : GetShopDetailReq){
        ApiShopService.Api.api.getShopDetail(SplashActivity.token,req).enqueue(object : Callback<MainGetShopDetailRes>{
            override fun onResponse(call: Call<MainGetShopDetailRes>, response: Response<MainGetShopDetailRes>) {
                if(response.isSuccessful){
                    assignmentItf.getShopDetail(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetShopDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getParcelWh(req : GetParcelWhReq){
        ApiWarehouseService.Api.api.getParcelWh(SplashActivity.token,req).enqueue(object : Callback<MainGetParcelWhRes>{
            override fun onResponse(call: Call<MainGetParcelWhRes>, response: Response<MainGetParcelWhRes>) {
                if(response.isSuccessful){
                    assignmentItf.getParcelWh(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetParcelWhRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun convertAdressToLocation(geocoder: Geocoder,strAdress: String, location : Location){
        var addressList: List<Address>
        try {
            addressList = geocoder.getFromLocationName(strAdress, 1)
            if (addressList != null) {
                location.latitude = addressList.get(0).latitude
                location.longitude = addressList.get(0).longitude
            }
        } catch (e: IOException) {
            println("Địa chỉ không hợp lệ")
            e.printStackTrace()
        }
    }

    fun callAPIGraphhopperRes(pointSource : String, pointDes : String, i: Int, j : Int){
        var profile = SplashActivity.API_RPOFILE
        var locale =  SplashActivity.API_LOCALE
        var calc_points = SplashActivity.API_CALC_POINTS
        var key = SplashActivity.API_KEY
        GraphhopperService.Api.api.getDistanceApi(
            pointSource,pointDes,profile,locale, calc_points, key).enqueue(object :
            Callback<GraphhopperRes> {
            override fun onResponse(call: Call<GraphhopperRes>, response: Response<GraphhopperRes>) {
                if(response.isSuccessful){
                    var distance = response.body()!!.paths!!.get(0).distance
                    println("distance: "+distance)
                    if( j == (-1) ){
                        AssignmentMngActivity.distanceStore[i]=(distance!!)
                    }
                    else if(j == (-2)){
                        AssignmentMngActivity.distanceCustomer[i]=(distance!!)
                    }
                    else{
                        AssignmentMngActivity.arrDistanceWH[i][j]=distance!!
                    }

                }
            }

            override fun onFailure(call: Call<GraphhopperRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun ShortestP(sourceV: Vert) {
        sourceV.setDist(0.0)
        var priorityQueue: PriorityQueue<Vert> = PriorityQueue<Vert>()
        priorityQueue.add(sourceV)
        sourceV.setVisited(true)
        while (!priorityQueue.isEmpty()) {
            var actualVertex: Vert = priorityQueue.poll()
            for (edge in actualVertex.getList()!!) {
                var v: Vert = edge!!.targetVert
                if (v.Visited() == false) {
                    var newDistance: Double = actualVertex.getDist() + edge.weight
                    if (newDistance < v.getDist()) {
                        priorityQueue.remove(v)
                        v.setDist(newDistance)
                        v.setPr(actualVertex)
                        priorityQueue.add(v)
                    }
                }
            }
            actualVertex.setVisited(true)
        }
    }

    fun getShortestP(targetVertex: Vert, vertStore : Vert): List<Vert> {
        var path = ArrayList<Vert>()
        var vertex = targetVertex
        path.add(vertex)
        while (vertex != vertStore) {  //while (vertex != vA)
            vertex = vertex.getPr()!!
            path.add(vertex)
        }
        return path.asReversed()
    }

    fun addWay(req : WayReq, size : Int){
        ApiWayService.Api.api.addWay(SplashActivity.token,req).enqueue(object : Callback<WayRes>{
            override fun onResponse(call: Call<WayRes>, response: Response<WayRes>) {
                if(response.isSuccessful){
                    println("thêm thành công")
                    if(req.stt == (size)){
                        assignmentItf.addWaySuccess()
                    }
                }
            }

            override fun onFailure(call: Call<WayRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getFilter(s: String){
        AssignmentMngActivity.listFilter = ArrayList<AdGetParcelRes>()
        for(list in AssignmentMngActivity.listParcel){
            if(list.mabk.toString().toUpperCase().contains(s.toUpperCase())){
                AssignmentMngActivity.listFilter.add(list)
            }
        }
        assignmentItf.getListParcel(AssignmentMngActivity.listFilter)
    }

}