package vn.vunganyen.fastdelivery.screens.admin.statistics.turnover

import android.telecom.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiParcelService
import vn.vunganyen.fastdelivery.data.model.turnover.MainTurnoverRes
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverReq
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.text.ParseException
import java.util.*

class TurnoverPst {
    var turnoverItf : TurnoverItf

    constructor(turnoverItf: TurnoverItf) {
        this.turnoverItf = turnoverItf
    }

    fun validCheck(req: TurnoverReq){
        if(req.dateFrom.isEmpty() || req.dateTo.isEmpty()){
            turnoverItf.Empty()
            return
        }
        val result = req.dateTo.compareTo(req.dateFrom)
        if(result < 0 || result == 0){
            turnoverItf.DateError()
            return
        }
        getTurnover(req)
    }

    fun getTurnover(req : TurnoverReq){
        ApiParcelService.Api.api.admin_turnover(SplashActivity.token,req).enqueue(object : Callback<MainTurnoverRes>{
            override fun onResponse(call: retrofit2.Call<MainTurnoverRes>, response: Response<MainTurnoverRes>) {
                if(response.isSuccessful){
                    handle(response.body()!!.result)
                }
            }

            override fun onFailure(call: retrofit2.Call<MainTurnoverRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun handle(list : List<TurnoverRes>){
        val dates: MutableList<String> = ArrayList<String>()
        val beginCalendar = Calendar.getInstance()
        val finishCalendar = Calendar.getInstance()
        try {
            beginCalendar.time = SplashActivity.formatMonthYear.parse(TurnoverActivity.dateStr)
            finishCalendar.time = SplashActivity.formatMonthYear.parse(TurnoverActivity.dateEnd)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        while(beginCalendar.compareTo(finishCalendar) <=0 ){
            var date = SplashActivity.formatMonthYear.format(beginCalendar.time).uppercase(Locale.getDefault())
            dates.add(date)
            beginCalendar.add(Calendar.MONTH, 1)
        }
        for(i in 0..dates.size-1){
            var check = 0
            val str: List<String> = dates.get(i).split("-")
            var month = str.get(0).toInt()
            var year = str.get(1).toInt()
            for(j in 0..list.size-1){
                if(month == list.get(j).thang && year == list.get(j).nam){
                    TurnoverActivity.listNew.add(TurnoverRes(month,year,list.get(j).doanhthu))
                    TurnoverActivity.sum = TurnoverActivity.sum + list.get(j).doanhthu
                    check = 1
                }
            }
            if(check == 0){
                TurnoverActivity.listNew.add(TurnoverRes(month,year,0.0F))
            }
        }
        turnoverItf.getTurnover(TurnoverActivity.listNew)
    }
}