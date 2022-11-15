package vn.vunganyen.fastdelivery.screens.admin.myProfile

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiRoleService
import vn.vunganyen.fastdelivery.data.api.ApiStaffService
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.role.RoleReq
import vn.vunganyen.fastdelivery.data.model.role.RoleRes
import vn.vunganyen.fastdelivery.data.model.staff.*
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class AdminMyProfilePst {
    var adminMyProfileItf : AdminMyProfileItf
    var gson = Gson()

    constructor(adminMyProfileItf: AdminMyProfileItf) {
        this.adminMyProfileItf = adminMyProfileItf
    }

    fun getRoleDetail(req : RoleReq){
        ApiRoleService.Api.api.getRoleDetail(SplashActivity.token,req).enqueue(object : Callback<MainListRoleRes>{
            override fun onResponse(call: Call<MainListRoleRes>, response: Response<MainListRoleRes>) {
                if(response.isSuccessful){
                    adminMyProfileItf.getRoleDetail(response.body()!!.result.get(0))
                }
            }

            override fun onFailure(call: Call<MainListRoleRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun validCheckUpdate(req: AdminUpdateReq) {
        if (req.manv.isEmpty() || req.hoten.isEmpty() || req.ngaysinh.isEmpty() || req.gioitinh.isEmpty() || req.diachi.isEmpty() ||
            req.email.isEmpty() || req.sdt.isEmpty()){
            adminMyProfileItf.Empty()
            return
        }
        if (!SplashActivity.CMND.matcher(req.cmnd).matches()) {
            adminMyProfileItf.CmndIllegal()
            return
        }
        if (!SplashActivity.SDT.matcher(req.sdt).matches()) {
            adminMyProfileItf.PhoneIllegal()
            return
        }
        if (!SplashActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            adminMyProfileItf.EmailIllegal()
            return
        }
        val current : String = SplashActivity.formatYear.format(Date())
        val str: List<String> = req.ngaysinh.split("-")
        var year = str.get(0).toInt()
        var old = current.toInt() - year
        if(old < 18){
            adminMyProfileItf.OrlError()
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
                        if(AdminMyProfileActivity.profileAd.cmnd.equals(check)){
                            checkPhoneExist(req)
                        }
                        else adminMyProfileItf.CmndExist()
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
                        if(AdminMyProfileActivity.profileAd.sdt.equals(check)){
                            checkEmailExist(req)
                        }
                        else adminMyProfileItf.PhoneExist()
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
                        adminMyProfileItf.CheckSuccess()
                    }
                    else{
                        if(AdminMyProfileActivity.profileAd.email.equals(check)){
                            adminMyProfileItf.CheckSuccess()
                        }
                        else  adminMyProfileItf.EmailExist()
                    }
                }
            }
            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateProfile(req : AdminUpdateReq){
        var tendangnhap = AdminMyProfileActivity.profileAd.tendangnhap
        var token = SplashActivity.token
        var roleId= SplashActivity.roleId
        ApiStaffService.Api.api.adminUpdateStaff(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    SplashActivity.editor.clear().apply()

                    SplashActivity.editor.putString("token",token)
                    SplashActivity.editor.putInt("roleId",roleId)
                    getProfile(SplashActivity.token,ProfileReq(tendangnhap))
                }
            }
            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getProfile(token : String, req: ProfileReq){
        ApiStaffService.Api.api.getProfile(token,req).enqueue(object : Callback<MainProfileRes>{
            override fun onResponse(call: Call<MainProfileRes>, response: Response<MainProfileRes>) {
                if(response.isSuccessful){
                    setProfile(response.body()!!)
                    adminMyProfileItf.updateSuccess()
                }
            }

            override fun onFailure(call: Call<MainProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setProfile(response : MainProfileRes){
        var strResponse = gson.toJson(response).toString()
        SplashActivity.editor.putString("profile",strResponse)
    }

    fun getProfileEditor(){
        var strResponse =  SplashActivity.sharedPreferences.getString("profile","")
        SplashActivity.profile = gson.fromJson(strResponse, MainProfileRes::class.java)
    }
}