package vn.vunganyen.fastdelivery.screens.staff.parcelDetail

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.MainGetShopDetailRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class StaffParcelDetailPst {
    var staffParcelDetailItf : StaffParcelDetailItf

    constructor(staffParcelDetailItf: StaffParcelDetailItf) {
        this.staffParcelDetailItf = staffParcelDetailItf
    }

    fun getShopDetail(req : GetShopDetailReq){
        ApiShopService.Api.api.getShopDetail(SplashActivity.token,req).enqueue(object :
            Callback<MainGetShopDetailRes> {
            override fun onResponse(call: Call<MainGetShopDetailRes>, response: Response<MainGetShopDetailRes>) {
                if(response.isSuccessful){
                    staffParcelDetailItf.getShopDetail(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetShopDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}