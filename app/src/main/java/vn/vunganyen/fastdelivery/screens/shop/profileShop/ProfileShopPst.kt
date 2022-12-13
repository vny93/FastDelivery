package vn.vunganyen.fastdelivery.screens.shop.profileShop

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.shop.AddShopReq
import vn.vunganyen.fastdelivery.data.model.shop.MainShopProfileRes
import vn.vunganyen.fastdelivery.data.model.shop.UpdatePhoneShopReq
import vn.vunganyen.fastdelivery.data.model.shop.UpdateShopReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckPhoneReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckProfileRes
import vn.vunganyen.fastdelivery.data.model.staff.StaffUpdateReq
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.myProfile.ProfileStSpActivity

class ProfileShopPst {
    var profileShopItf : ProfileShopItf
    var gson = Gson()

    constructor(profileShopItf: ProfileShopItf) {
        this.profileShopItf = profileShopItf
    }

    fun validCheckUpdate(req: UpdatePhoneShopReq) {
        println("check")
        if (req.sdt.isEmpty()){
            println("rỗng")
            profileShopItf.Empty()
            return
        }
        if (!SplashActivity.SDT.matcher(req.sdt).matches()) {
            println("vô")
            profileShopItf.PhoneIllegal()
            return
        }
        println("ra")
        checkPhoneExist(req)
    }

    fun checkPhoneExist(req: UpdatePhoneShopReq){
        ApiShopService.Api.api.checkPhone(CheckPhoneReq(req.sdt)).enqueue(object :
            Callback<CheckProfileRes> {
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        profileShopItf.CheckSuccess()
                    }
                    else{
                        println(ProfileShopActivity.profileShop.sdt)
                        if(ProfileShopActivity.profileShop.sdt.equals(req.sdt)){
                            profileShopItf.CheckSuccess()
                        }
                        else profileShopItf.PhoneExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateProfileShop(req : UpdatePhoneShopReq){
        var tendangnhap = ProfileShopActivity.profileShop.tendangnhap
        var token = SplashActivity.token
        var roleId= SplashActivity.roleId
        ApiShopService.Api.api.updatePhone(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    SplashActivity.editor.clear().apply()

                    SplashActivity.editor.putString("token",token)
                    SplashActivity.editor.putInt("roleId",roleId)
                    getProfileStore(SplashActivity.token, ProfileReq(tendangnhap))
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getProfileStore(token : String, req : ProfileReq){
        ApiShopService.Api.api.getProfile(token,req).enqueue(object : Callback<MainShopProfileRes>{
            override fun onResponse(call: Call<MainShopProfileRes>, response: Response<MainShopProfileRes>) {
                if(response.isSuccessful){
                    setProfileShop(response.body()!!)
                    profileShopItf.updateSuccess()
                }
            }

            override fun onFailure(call: Call<MainShopProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        //làm sau
    }
    fun setProfileShop(response : MainShopProfileRes){
        var strResponse = gson.toJson(response).toString()
        SplashActivity.editor.putString("profileShop",strResponse)
    }

    fun getProfileEditor(){
        var strResponse =  SplashActivity.sharedPreferences.getString("profileShop","")
        SplashActivity.profileShop = gson.fromJson(strResponse, MainShopProfileRes::class.java)
    }
}