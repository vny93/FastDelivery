package vn.vunganyen.fastdelivery.screens.shop.registerShop

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiAuthService
import vn.vunganyen.fastdelivery.data.api.ApiDistrictService
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterRes
import vn.vunganyen.fastdelivery.data.model.classSupport.MD5Hash
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.shop.AddShopReq
import vn.vunganyen.fastdelivery.data.model.shop.MainAutoIdRes
import vn.vunganyen.fastdelivery.data.model.staff.*
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class ShopRegisterPst {
    var shopRegisterItf : ShopRegisterItf
    var md5 : MD5Hash = MD5Hash()

    constructor(shopRegisterItf: ShopRegisterItf) {
        this.shopRegisterItf = shopRegisterItf
    }

    fun autoId(){
        ApiShopService.Api.api.autoId().enqueue(object : Callback<MainAutoIdRes>{
            override fun onResponse(call: Call<MainAutoIdRes>, response: Response<MainAutoIdRes>) {
                if(response.isSuccessful){
                    shopRegisterItf.AutomaticId(response.body()!!.result.get(0).mach)
                }
            }

            override fun onFailure(call: Call<MainAutoIdRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes>{
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    shopRegisterItf.getListDistrict(response.body()!!.districts)
                }
            }

            override fun onFailure(call: Call<MainGetDistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getWards(code : Long){
        ApiDistrictService.Api.api.getDistrict2(code).enqueue(object : Callback<DistrictRes>{
            override fun onResponse(call: Call<DistrictRes>, response: Response<DistrictRes>) {
                if(response.isSuccessful){
                    shopRegisterItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun constrainCheck(req1 : AuthRegisterReq, req2 : AddShopReq){
        if (req1.tendangnhap.isEmpty() || req1.matkhau.isEmpty() || req2.tench.isEmpty() || req2.sdt.isEmpty() ||
                req2.email.isEmpty() || req2.diachi.isEmpty()){
            shopRegisterItf.Empty()
            return
        }
        if(!SplashActivity.USERNAME.matcher(req1.tendangnhap).matches()){
            shopRegisterItf.UserIllegal()
            return
        }
        if(!SplashActivity.PASSWORD.matcher(req1.matkhau).matches()){
            shopRegisterItf.PassIllegal()
            return
        }
        if (!SplashActivity.SDT.matcher(req2.sdt).matches()) {
            shopRegisterItf.PhoneIllegal()
            return
        }
        if (!SplashActivity.EMAIL_ADDRESS.matcher(req2.email).matches()) {
            shopRegisterItf.EmailIllegal()
            return
        }
        checkAccountExist(req1,req2)
    }

    fun checkAccountExist(req1 : AuthRegisterReq, req2 : AddShopReq){
        ApiAuthService.Api.api.findAuth(ProfileReq(req1.tendangnhap)).enqueue(object :
            Callback<CheckWordRes> {
            override fun onResponse(call: Call<CheckWordRes>, response: Response<CheckWordRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == true){
                        shopRegisterItf.AccountExist()
                    }
                    else checkPhoneExist(req2)
                }
            }

            override fun onFailure(call: Call<CheckWordRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkPhoneExist(req: AddShopReq){
        ApiShopService.Api.api.checkPhone(CheckPhoneReq(req.sdt)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        checkEmailExist(req)
                    }
                    else shopRegisterItf.PhoneExist()
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkEmailExist(req : AddShopReq){
        ApiShopService.Api.api.checkEmail(CheckEmailReq(req.email)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        shopRegisterItf.CheckSuccess()
                    }
                    else shopRegisterItf.EmailExist()
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertAccount(req1 : AuthRegisterReq, req2 : AddShopReq){
        req1.matkhau = md5.md5Code(req1.matkhau)
        println("mật khẩu: "+req1.matkhau)
        ApiShopService.Api.api.registerAccount(req1).enqueue(object : Callback<AuthRegisterRes>{
            override fun onResponse(call: Call<AuthRegisterRes>, response: Response<AuthRegisterRes>) {
                if(response.isSuccessful){
                    insertShop(req2)
                }
            }

            override fun onFailure(call: Call<AuthRegisterRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertShop(req : AddShopReq){
        ApiShopService.Api.api.add(req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    shopRegisterItf.AddSucces()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}