package vn.vunganyen.fastdelivery.screens.admin.statistics.statistics

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.api.ApiSalaryService
import vn.vunganyen.fastdelivery.data.model.parcel.MainStatistics1Res
import vn.vunganyen.fastdelivery.data.model.parcel.MainStatistics2Res
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics1Req
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics2Req
import vn.vunganyen.fastdelivery.data.model.salary.MainCollectionRes
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
        ApiParcelService.Api.api.admin_statistics2(SplashActivity.token,req).enqueue(object : Callback<MainStatistics1Res>{
            override fun onResponse(call: Call<MainStatistics1Res>, response: Response<MainStatistics1Res>) {
                if(response.isSuccessful){
                    if(req.trangthai.equals(tt1)){
                        statisticItf.success(response.body()!!.result)
                    }
                    else if(req.trangthai.equals(tt2)){
                        statisticItf.fail(response.body()!!.result)
                    }
                    else{
                        statisticItf.toReturn(response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<MainStatistics1Res>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getStatistics3(req: Statistics1Req){
        ApiParcelService.Api.api.admin_statistics3(SplashActivity.token,req).enqueue(object : Callback<MainStatistics1Res>{
            override fun onResponse(call: Call<MainStatistics1Res>, response: Response<MainStatistics1Res>) {
                if(response.isSuccessful){
                    statisticItf.shipping(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainStatistics1Res>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun admin_collection(req : Statistics1Req){
        ApiSalaryService.Api.api.admin_collection(SplashActivity.token,req).enqueue(object : Callback<MainCollectionRes>{
            override fun onResponse(call: Call<MainCollectionRes>, response: Response<MainCollectionRes>) {
                if(response.isSuccessful){
                    statisticItf.get_list_collection(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainCollectionRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}