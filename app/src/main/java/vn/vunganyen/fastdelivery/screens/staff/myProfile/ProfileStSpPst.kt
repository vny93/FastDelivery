package vn.vunganyen.fastdelivery.screens.staff.myProfile

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiRoleService
import vn.vunganyen.fastdelivery.data.api.ApiStaffService
import vn.vunganyen.fastdelivery.data.api.ApiWarehouseService
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.role.RoleReq
import vn.vunganyen.fastdelivery.data.model.staff.*
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ProfileStSpPst {
    var profileStSpItf : ProfileStSpItf
    var gson = Gson()

    constructor(profileStSpItf: ProfileStSpItf) {
        this.profileStSpItf = profileStSpItf
    }

    fun getRoleDetail(req : RoleReq){
        ApiRoleService.Api.api.getRoleDetail(SplashActivity.token,req).enqueue(object :
            Callback<MainListRoleRes> {
            override fun onResponse(call: Call<MainListRoleRes>, response: Response<MainListRoleRes>) {
                if(response.isSuccessful){
                    profileStSpItf.getRoleDetail(response.body()!!.result.get(0))
                }
            }

            override fun onFailure(call: Call<MainListRoleRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDetailWH(req : GetDetailWHReq){
        ApiWarehouseService.Api.api.getDetail(SplashActivity.token,req).enqueue(object :Callback<GetDetailWHRes>{
            override fun onResponse(call: Call<GetDetailWHRes>, response: Response<GetDetailWHRes>) {
                if(response.isSuccessful){
                    profileStSpItf.getDetailWH(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<GetDetailWHRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun validCheckUpdate(req: StaffUpdateReq) {
        if (req.sdt.isEmpty()){
            profileStSpItf.Empty()
            return
        }
        if (!SplashActivity.SDT.matcher(req.sdt).matches()) {
            profileStSpItf.PhoneIllegal()
            return
        }
        checkPhoneExist(req)

    }

    fun checkPhoneExist(req : StaffUpdateReq){
        ApiStaffService.Api.api.checkPhone(SplashActivity.token, CheckPhoneReq(req.sdt)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        profileStSpItf.CheckSuccess()
                    }
                    else{
                        println(ProfileStSpActivity.profileStSp.sdt)
                        println(check)
                        if(ProfileStSpActivity.profileStSp.sdt.equals(check)){
                            println("vô đây á ?")
                            profileStSpItf.CheckSuccess()
                        }
                        else  profileStSpItf.PhoneExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateProfile(req : StaffUpdateReq){
        var tendangnhap = ProfileStSpActivity.profileStSp.tendangnhap
        var token = SplashActivity.token
        var roleId= SplashActivity.roleId
        ApiStaffService.Api.api.staffUpdate(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    SplashActivity.editor.clear().apply()

                    SplashActivity.editor.putString("token",token)
                    SplashActivity.editor.putInt("roleId",roleId)
                    getProfile(SplashActivity.token, ProfileReq(tendangnhap))
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
                    profileStSpItf.updateSuccess()
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