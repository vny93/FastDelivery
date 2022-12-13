package vn.vunganyen.fastdelivery.screens.staff.parcelDetail

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDetailParcelService
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelReq
import vn.vunganyen.fastdelivery.data.model.detailParcel.MainGetDetailParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.FullStatusDetailReq
import vn.vunganyen.fastdelivery.data.model.parcel.MainStatusDetailRes
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

    fun getDateilParcel(req : GetDetailParcelReq){
        ApiDetailParcelService.Api.api.get_detail_parcel(SplashActivity.token,req).enqueue(object : Callback<MainGetDetailParcelRes>{
            override fun onResponse(call: Call<MainGetDetailParcelRes>, response: Response<MainGetDetailParcelRes>) {
                if(response.isSuccessful){
                    staffParcelDetailItf.getDetailParcel(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetDetailParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fullStatusDetail(req : FullStatusDetailReq){
        ApiParcelService.Api.api.full_status_detail(SplashActivity.token,req).enqueue(object : Callback<MainStatusDetailRes>{
            override fun onResponse(call: Call<MainStatusDetailRes>, response: Response<MainStatusDetailRes>) {
                if(response.isSuccessful){
                    staffParcelDetailItf.fullStatusDetail(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainStatusDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}