package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.shop.*
import vn.vunganyen.fastdelivery.data.model.staff.CheckEmailReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckPhoneReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckProfileRes


interface ApiShopService {

    @GET("v2/shop/get/list")
    fun getListShop(@Header("Authorization") BearerToken: String):Call<GetListShopRes>

    @POST("v2/shop/get/detail")
    fun getShopDetail(@Header("Authorization") BearerToken: String, @Body req: GetShopDetailReq):Call<MainGetShopDetailRes>

    @POST("v2/shop/checkPhone")
    fun checkPhone(@Header("Authorization") BearerToken: String, @Body req: CheckPhoneReq): Call<CheckProfileRes>

    @POST("v2/shop/checkEmail")
    fun checkEmail(@Header("Authorization") BearerToken: String, @Body req: CheckEmailReq): Call<CheckProfileRes>

    @PUT("v2/shop/admin/update")
    fun update(@Header("Authorization") BearerToken: String, @Body req: UpdateShopReq):Call<UpdateRes>

    object Api {
        val api: ApiShopService by lazy { RetrofitSetting().retrofit.create(ApiShopService::class.java) }
    }

}