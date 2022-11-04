package vn.vunganyen.fastdelivery.screens.admin.staffMng.getList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiStaffService
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWorkReq
import vn.vunganyen.fastdelivery.data.model.staff.MainListStaffRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class StaffMngPst {
    var staffMngItf : StaffMngItf

    constructor(staffMngItf: StaffMngItf) {
        this.staffMngItf = staffMngItf
    }

    fun getFullStaff(){
        ApiStaffService.Api.api.getFullStaff(SplashActivity.token).enqueue(object : Callback<MainListStaffRes>{
            override fun onResponse(call: Call<MainListStaffRes>, response: Response<MainListStaffRes>) {
                if(response.isSuccessful){
                    staffMngItf.getFullStaff(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainListStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkStaffWork(req : CheckWorkReq){
        ApiStaffService.Api.api.checkStaffWord(SplashActivity.token,req).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result == true){
                        staffMngItf.deleteFail()
                    }
                    else staffMngItf.allowDetele(req)
                }
            }
            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun deleteStaff(req: CheckWorkReq){
        ApiStaffService.Api.api.deleteStaff(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    staffMngItf.deleteSuccess()
                }
            }
            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}