package vn.vunganyen.fastdelivery.screens.shipper.shipperStatistics

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.model.parcel.MainShipperStatistics
import vn.vunganyen.fastdelivery.data.model.parcel.ShipperStatisticsReq
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class ShipperStatisticPst {
    var shipperStatisticsItf : ShipperStatisticsItf

    constructor(shipperStatisticsItf: ShipperStatisticsItf) {
        this.shipperStatisticsItf = shipperStatisticsItf
    }

    fun findEndOfMonth(cal: Calendar) : Calendar {
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.HOUR_OF_DAY, -7)
      //  cal.add(Calendar.DATE, -1)
        return cal
    }

    fun shipperStatistics(req : ShipperStatisticsReq){
        ApiParcelService.Api.api.shipper_statistics(SplashActivity.token,req).enqueue(object : Callback<MainShipperStatistics>{
            override fun onResponse(call: Call<MainShipperStatistics>, response: Response<MainShipperStatistics>) {
                if(response.isSuccessful){
                    shipperStatisticsItf.getListSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainShipperStatistics>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}