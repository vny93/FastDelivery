package vn.vunganyen.fastdelivery.screens.shop.parcelShop

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistrictService
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.api.ApiStatusService
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.parcel.MainSpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.ShopGetParcelReq
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.screens.shipper.parcelSpMng.ShipperParcelFgm
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ShopParcelPst {
    var shopParcelItf : ShopParcelItf

    constructor(shopParcelItf: ShopParcelItf) {
        this.shopParcelItf = shopParcelItf
    }

    fun getListStatus(){
        ApiStatusService.Api.api.getListStatus(SplashActivity.token).enqueue(object :
            Callback<MainListStatusRes> {
            override fun onResponse(call: Call<MainListStatusRes>, response: Response<MainListStatusRes>) {
                if(response.isSuccessful){
                    shopParcelItf.getListStatus(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainListStatusRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes> {
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    shopParcelItf.getListDistrict(response.body()!!.districts)
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
                    shopParcelItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun filterParcel(req : ShopGetParcelReq){
        println("vô")
        ApiShopService.Api.api.shop_get_parcel(SplashActivity.token,req).enqueue(object :
            Callback<MainSpGetParcelRes> {
            override fun onResponse(call: Call<MainSpGetParcelRes>, response: Response<MainSpGetParcelRes>) {
                if(response.isSuccessful){
                    println("????")
                    ShopParcelFgm.listParcel = response.body()!!.result as ArrayList<SpGetParcelRes>
                    shopParcelItf.getListParcel(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainSpGetParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getFilter(s: String){
        ShopParcelFgm.listFilter = ArrayList<SpGetParcelRes>()
        for(list in ShopParcelFgm.listParcel){
            if(list.mabk.toString().toUpperCase().contains(s.toUpperCase())){
                ShopParcelFgm.listFilter.add(list)
            }
        }
        shopParcelItf.getListParcel(ShopParcelFgm.listFilter)
    }
}