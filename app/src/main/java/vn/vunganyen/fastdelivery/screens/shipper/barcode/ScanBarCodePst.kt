package vn.vunganyen.fastdelivery.screens.shipper.barcode

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.MainGetDetailParcelRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ScanBarCodePst {
    var scanBarCodeItf : ScanBarCodeItf

    constructor(scanBarCodeItf: ScanBarCodeItf) {
        this.scanBarCodeItf = scanBarCodeItf
    }

    fun getDetailParcel(req: GetDetailParcelReq){
        ApiParcelService.Api.api.get_detail_parcel(SplashActivity.token,req).enqueue(object : Callback<MainGetDetailParcelRes>{
            override fun onResponse(call: Call<MainGetDetailParcelRes>, response: Response<MainGetDetailParcelRes>) {
                if(response.isSuccessful){
                    scanBarCodeItf.getDetailParcel(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainGetDetailParcelRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}