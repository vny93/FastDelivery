package vn.vunganyen.fastdelivery.screens.shipper.map

import android.location.Address
import android.location.Geocoder
import android.location.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.api.GraphhopperService
import vn.vunganyen.fastdelivery.data.model.graphhopper.GraphhopperRes
import vn.vunganyen.fastdelivery.data.model.graphhopper.GraphhopperRes2
import vn.vunganyen.fastdelivery.data.model.graphhopper.ListPointsReq
import vn.vunganyen.fastdelivery.data.model.graphhopper.ListPointsRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.MainGetShopDetailRes
import vn.vunganyen.fastdelivery.screens.admin.parcelMng.assignment.AssignmentMngActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.io.IOException

class MapPst {
    var mapItf : MapItf

    constructor(mapItf: MapItf) {
        this.mapItf = mapItf
    }

    fun getShopDetail(req : GetShopDetailReq){
        ApiShopService.Api.api.getShopDetail(SplashActivity.token,req).enqueue(object : Callback<MainGetShopDetailRes>{
            override fun onResponse(call: Call<MainGetShopDetailRes>, response: Response<MainGetShopDetailRes>) {
                if(response.isSuccessful){
                    mapItf.getShopDetail(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetShopDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun convertAdressToLocation(geocoder: Geocoder, strAdress: String, location : Location){
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

    fun getMap(pointSource : String, pointDes : String){
        var profile = SplashActivity.API_RPOFILE
        var locale =  SplashActivity.API_LOCALE
        var calc_points = SplashActivity.API_CALC_POINTS2
        var key = SplashActivity.API_KEY
        GraphhopperService.Api.api.getMapApi(pointSource,pointDes,profile,locale, calc_points, key).enqueue(object : Callback<GraphhopperRes2> {
            override fun onResponse(call: Call<GraphhopperRes2>, response: Response<GraphhopperRes2>) {
                if(response.isSuccessful){
                    var points = response.body()!!.paths!!.get(0).points
                    println("points encoded: "+points)
                    mapItf.getEncoded(points)
                }
            }

            override fun onFailure(call: Call<GraphhopperRes2>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListPoint(req : ListPointsReq){
        ApiParcelService.Api.api.get_points_map(SplashActivity.token,req).enqueue(object : Callback<ListPointsRes>{
            override fun onResponse(call: Call<ListPointsRes>, response: Response<ListPointsRes>) {
                if(response.isSuccessful){
                    mapItf.getListPoint(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<ListPointsRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}