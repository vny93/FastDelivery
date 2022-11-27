package vn.vunganyen.fastdelivery.screens.staff.salary

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiSalaryService
import vn.vunganyen.fastdelivery.data.model.salary.MainShipperSalaryRes
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryReq
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class StaffSalaryPst {
    var staffSalaryItf : StaffSalaryItf

    constructor(staffSalaryItf: StaffSalaryItf) {
        this.staffSalaryItf = staffSalaryItf
    }

    fun findEndOfMonth(cal: Calendar) : Calendar {
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1)
        System.out.println("Start of the next month:  " + cal.getTime());
        return cal
    }

    fun staff_get_salary(req : ShipperSalaryReq){
        ApiSalaryService.Api.api.shipper_get_salary(SplashActivity.token,req).enqueue(object :
            Callback<MainShipperSalaryRes> {
            override fun onResponse(call: Call<MainShipperSalaryRes>, response: Response<MainShipperSalaryRes>) {
                if(response.isSuccessful){
                    staffSalaryItf.staff_get_salary(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainShipperSalaryRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}