package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.petshop.UpdateCartStatusReq

interface ApiCartPetShopSevice {

    @PUT("v1/cart/user/update/status")
    fun updateCartStatus(@Body req: UpdateCartStatusReq): Call<UpdateRes>

    object Api {
        val api: ApiCartPetShopSevice by lazy { RetrofitSettingPetShop().retrofit.create(ApiCartPetShopSevice::class.java) }
    }
}