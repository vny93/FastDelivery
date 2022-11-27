package vn.vunganyen.fastdelivery.screens.admin.statistics.statistics

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.model.parcel.MainStatistics1Res
import vn.vunganyen.fastdelivery.data.model.parcel.MainStatistics2Res
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics1Req
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics2Req
import vn.vunganyen.fastdelivery.screens.admin.statistics.statistics.StatisticsActivity.Companion.tt1
import vn.vunganyen.fastdelivery.screens.admin.statistics.statistics.StatisticsActivity.Companion.tt2
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class StatisticsPst {
    var statisticItf : StatisticItf

    constructor(statisticItf: StatisticItf) {
        this.statisticItf = statisticItf
    }

    fun getStatistics1(req: Statistics1Req){
        ApiParcelService.Api.api.admin_statistics1(SplashActivity.token,req).enqueue(object : Callback<MainStatistics1Res>{
            override fun onResponse(call: Call<MainStatistics1Res>, response: Response<MainStatistics1Res>) {
                if(response.isSuccessful){
                    statisticItf.getTt1(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainStatistics1Res>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getStatistics2(req : Statistics2Req){
        ApiParcelService.Api.api.admin_statistics2(SplashActivity.token,req).enqueue(object : Callback<MainStatistics2Res>{
            override fun onResponse(call: Call<MainStatistics2Res>, response: Response<MainStatistics2Res>) {
                if(response.isSuccessful){
                    if(req.trangthai.equals(tt1)){
                        statisticItf.getTt2(response.body()!!.result)
                    }
                    else if(req.trangthai.equals(tt2)){
                        statisticItf.getTt3(response.body()!!.result)
                    }
                    else{
                        statisticItf.getTt4(response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<MainStatistics2Res>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}