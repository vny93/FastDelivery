package vn.vunganyen.fastdelivery.screens.admin.staffMng.insert

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiAuthService
import vn.vunganyen.fastdelivery.data.api.ApiRoleService
import vn.vunganyen.fastdelivery.data.api.ApiStaffService
import vn.vunganyen.fastdelivery.data.api.ApiWarehouseService
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterRes
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.staff.*
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class InsertStaffPts {
    var insertStaffItf: InsertStaffItf

    constructor(insertStaffItf: InsertStaffItf) {
        this.insertStaffItf = insertStaffItf
    }

    fun constraintCheck(req1 : AuthRegisterReq, req2 : ProfileRes){
        if (req1.tendangnhap.isEmpty() || req1.matkhau.isEmpty() || req1.maquyen == 0){
            insertStaffItf.Empty()
            return
        }
        if(!SplashActivity.USERNAME.matcher(req1.tendangnhap).matches()){
            insertStaffItf.UserIllegal()
            return
        }
        if(!SplashActivity.PASSWORD.matcher(req1.matkhau).matches()){
            insertStaffItf.PassIllegal()
            return
        }
        checkAccountExist(req1,req2)
    }

    fun validCheckUpdate(req: ProfileRes) {
        if (req.manv.isEmpty() || req.hoten.isEmpty() || req.ngaysinh.isEmpty() || req.gioitinh.isEmpty() || req.diachi.isEmpty() ||
            req.email.isEmpty() || req.sdt.isEmpty()){
            insertStaffItf.Empty()
            return
        }
        if (!SplashActivity.CMND.matcher(req.cmnd).matches()) {
            insertStaffItf.CmndIllegal()
            return
        }
        if (!SplashActivity.SDT.matcher(req.sdt).matches()) {
            insertStaffItf.PhoneIllegal()
            return
        }
        if (!SplashActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            insertStaffItf.EmailIllegal()
            return
        }
        val current : String = SplashActivity.formatYear.format(Date())
        val str: List<String> = req.ngaysinh.split("-")
        var year = str.get(0).toInt()
        var old = current.toInt() - year
        if(old < 18){
            insertStaffItf.OrlError()
            return
        }
        else checkCmndExist(req)

    }

    fun getListRole(){
        ApiRoleService.Api.api.getListRole(SplashActivity.token).enqueue(object : Callback<MainListRoleRes>{
            override fun onResponse(call: Call<MainListRoleRes>, response: Response<MainListRoleRes>) {
                if(response.isSuccessful){
                    insertStaffItf.getRoleSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainListRoleRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListWarehouse(){
        ApiWarehouseService.Api.api.getListWarehouse(SplashActivity.token).enqueue(object : Callback<MainWarehouseRes>{
            override fun onResponse(call: Call<MainWarehouseRes>, response: Response<MainWarehouseRes>) {
                if(response.isSuccessful){
                    insertStaffItf.getWarehouseSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainWarehouseRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun automaticId(req : AutomaticIdReq){
        ApiStaffService.Api.api.automaticId(SplashActivity.token,req).enqueue(object : Callback<MainAutomaticIdRes>{
            override fun onResponse(call: Call<MainAutomaticIdRes>, response: Response<MainAutomaticIdRes>) {
                if(response.isSuccessful){
                    insertStaffItf.AutomaticId(response.body()!!.result.get(0).manv)
                }
            }

            override fun onFailure(call: Call<MainAutomaticIdRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkAccountExist(req1 : AuthRegisterReq, req2 : ProfileRes){
        ApiAuthService.Api.api.findAuth(ProfileReq(req1.tendangnhap)).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == true){
                        insertStaffItf.CmndExist()
                    }
                    else validCheckUpdate(req2)
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkCmndExist(req : ProfileRes){
        ApiStaffService.Api.api.checkCmnd(SplashActivity.token, CheckCmndReq(req.cmnd)).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == true){
                        insertStaffItf.CmndExist()
                    }
                    else checkPhoneExist(req)
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkPhoneExist(req : ProfileRes){
        ApiStaffService.Api.api.checkPhone(SplashActivity.token, CheckPhoneReq(req.sdt)).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == true){
                        insertStaffItf.PhoneExist()
                    }
                    else checkEmailExist(req)
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkEmailExist(req : ProfileRes){
        ApiStaffService.Api.api.checkEmail(SplashActivity.token, CheckEmailReq(req.email)).enqueue(object : Callback<CheckWordRes>{
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == true){
                        insertStaffItf.EmailExist()
                    }
                    else insertStaffItf.CheckSuccess()
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun insertAccount(req1 : AuthRegisterReq, req2 : ProfileRes, req3 : InsertShipperReq){
        ApiAuthService.Api.api.registerAccount(SplashActivity.token,req1).enqueue(object : Callback<AuthRegisterRes>{
            override fun onResponse(call: Call<AuthRegisterRes>, response: Response<AuthRegisterRes>) {
                if(response.isSuccessful){
                    if(InsertStaffActivity.roleId == SplashActivity.STAFF){
                        insertStaff(req2)
                    }
                    else insertShipper(req3)
                }
            }

            override fun onFailure(call: Call<AuthRegisterRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertStaff(req : ProfileRes){
        ApiStaffService.Api.api.insertStaff(SplashActivity.token,req).enqueue(object : Callback<MainProfileRes>{
            override fun onResponse(call: Call<MainProfileRes>, response: Response<MainProfileRes>) {
                if(response.isSuccessful){
                    insertStaffItf.AddSucces()
                }
            }

            override fun onFailure(call: Call<MainProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertShipper(req : InsertShipperReq){
        ApiStaffService.Api.api.insertShipper(SplashActivity.token,req).enqueue(object : Callback<MainProfileRes>{
            override fun onResponse(call: Call<MainProfileRes>, response: Response<MainProfileRes>) {
                if(response.isSuccessful){
                    insertStaffItf.AddSucces()
                }
            }

            override fun onFailure(call: Call<MainProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}