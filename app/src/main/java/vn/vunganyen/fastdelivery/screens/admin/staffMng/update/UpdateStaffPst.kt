package vn.vunganyen.fastdelivery.screens.admin.staffMng.update

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistrictService
import vn.vunganyen.fastdelivery.data.api.ApiStaffService
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.staff.*
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class UpdateStaffPst {
    var updateStaffItf : UpdateStaffItf

    constructor(updateStaffItf: UpdateStaffItf) {
        this.updateStaffItf = updateStaffItf
    }

    fun validCheckUpdate(req: AdminUpdateReq) {
        if (req.manv.isEmpty() || req.hoten.isEmpty() || req.ngaysinh.isEmpty() || req.gioitinh.isEmpty() || req.diachi.isEmpty() ||
            req.email.isEmpty() || req.sdt.isEmpty()){
            updateStaffItf.Empty()
            return
        }
        if (!SplashActivity.CMND.matcher(req.cmnd).matches()) {
            updateStaffItf.CmndIllegal()
            return
        }
        if (!SplashActivity.SDT.matcher(req.sdt).matches()) {
            updateStaffItf.PhoneIllegal()
            return
        }
        if (!SplashActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            updateStaffItf.EmailIllegal()
            return
        }
        val current : String = SplashActivity.formatYear.format(Date())
        val str: List<String> = req.ngaysinh.split("-")
        var year = str.get(0).toInt()
        var old = current.toInt() - year
        if(old < 18){
            updateStaffItf.OrlError()
            return
        }
        else checkCmndExist(req)

    }

    fun checkCmndExist(req : AdminUpdateReq){
        ApiStaffService.Api.api.checkCmnd(SplashActivity.token, CheckCmndReq(req.cmnd)).enqueue(object :
            Callback<CheckProfileRes> {
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        checkPhoneExist(req)
                    }
                    else{
                        if(UpdateStaffActivity.staff.cmnd.equals(check)){
                            checkPhoneExist(req)
                        }
                        else updateStaffItf.CmndExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkPhoneExist(req : AdminUpdateReq){
        ApiStaffService.Api.api.checkPhone(SplashActivity.token, CheckPhoneReq(req.sdt)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        checkEmailExist(req)
                    }
                    else{
                        if(UpdateStaffActivity.staff.sdt.equals(check)){
                            checkEmailExist(req)
                        }
                        else updateStaffItf.PhoneExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkEmailExist(req : AdminUpdateReq){
        ApiStaffService.Api.api.checkEmail(SplashActivity.token, CheckEmailReq(req.email)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        updateStaffItf.CheckSuccess()
                    }
                    else{
                        if(UpdateStaffActivity.staff.email.equals(check)){
                            updateStaffItf.CheckSuccess()
                        }
                        else  updateStaffItf.EmailExist()
                    }
                }
            }
            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateProfile(req : AdminUpdateReq){
        ApiStaffService.Api.api.adminUpdateStaff(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    updateStaffItf.updateSuccess()
                }
            }
            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes>{
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    updateStaffItf.getListDistrict(response.body()!!.districts)
                }
            }

            override fun onFailure(call: Call<MainGetDistrictRes>, t: Throwable) {
                println("UpdateStaffPst Error getDistrict()")
            }

        })
    }

    fun getWards(code : Long){
        ApiDistrictService.Api.api.getDistrict2(code).enqueue(object : Callback<DistrictRes>{
            override fun onResponse(call: Call<DistrictRes>, response: Response<DistrictRes>) {
                if(response.isSuccessful){
                    updateStaffItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}