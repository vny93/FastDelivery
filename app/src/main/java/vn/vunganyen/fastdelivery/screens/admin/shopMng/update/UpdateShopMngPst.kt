package vn.vunganyen.fastdelivery.screens.admin.shopMng.update

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiDistrictService
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.shop.UpdateShopReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckEmailReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckPhoneReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckProfileRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class UpdateShopMngPst {
    var updateShopMngItf : UpdateShopMngItf

    constructor(updateShopMngItf: UpdateShopMngItf) {
        this.updateShopMngItf = updateShopMngItf
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes> {
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    updateShopMngItf.getListDistrict(response.body()!!.districts)
                }
            }

            override fun onFailure(call: Call<MainGetDistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getWards(code : Long){
        ApiDistrictService.Api.api.getDistrict2(code).enqueue(object : Callback<DistrictRes> {
            override fun onResponse(call: Call<DistrictRes>, response: Response<DistrictRes>) {
                if(response.isSuccessful){
                    updateShopMngItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun constraintCheck(req : UpdateShopReq) {
        if (req.tench.isEmpty() || req.diachi.isEmpty() || req.email.isEmpty() || req.sdt.isEmpty() ) {
            updateShopMngItf.Empty()
            return
        }
        if (!SplashActivity.SDT.matcher(req.sdt).matches()) {
            updateShopMngItf.PhoneIllegal()
            return
        }
        if (!SplashActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            updateShopMngItf.EmailIllegal()
            return
        }
        checkPhoneExist(req)
    }

    fun checkPhoneExist(req : UpdateShopReq){
        ApiShopService.Api.api.checkPhone(SplashActivity.token, CheckPhoneReq(req.sdt)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        checkEmailExist(req)
                    }
                    else{
                        if(UpdateShopMngActivity.shop.sdt.equals(check)){
                            checkEmailExist(req)
                        }
                        else updateShopMngItf.PhoneExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkEmailExist(req : UpdateShopReq){
        ApiShopService.Api.api.checkEmail(SplashActivity.token, CheckEmailReq(req.email)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        updateShop(req)
                    }
                    else{
                        if(UpdateShopMngActivity.shop.email.equals(check)){
                            updateShop(req)
                        }
                        else updateShopMngItf.EmailExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateShop(req : UpdateShopReq){
        ApiShopService.Api.api.update(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    updateShopMngItf.UpdateSucces()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}