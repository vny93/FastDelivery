package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.parcel.MainSpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelReq
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.shop.*
import vn.vunganyen.fastdelivery.data.model.staff.CheckEmailReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckPhoneReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckProfileRes


interface ApiShopService {

    @GET("v2/shop/get/list")
    fun getListShop(@Header("Authorization") BearerToken: String):Call<GetListShopRes>

    @POST("v2/shop/get/profile")
    fun getProfile(@Header("Authorization") BearerToken: String, @Body req: ProfileReq): Call<MainShopProfileRes>

    @POST("v2/shop/get/detail")
    fun getShopDetail(@Header("Authorization") BearerToken: String, @Body req: GetShopDetailReq):Call<MainGetShopDetailRes>

    @POST("v2/shop/checkPhone")
    fun checkPhone(@Body req: CheckPhoneReq): Call<CheckProfileRes>

    @POST("v2/shop/checkEmail")
    fun checkEmail(@Body req: CheckEmailReq): Call<CheckProfileRes>

    @PUT("v2/shop/admin/update")
    fun update(@Header("Authorization") BearerToken: String, @Body req: UpdateShopReq):Call<UpdateRes>

    @POST("v2/shop/register")
    fun add(@Body req: AddShopReq):Call<UpdateRes>

    @POST("v2/auth/shop/register")
    fun registerAccount( @Body req: AuthRegisterReq): Call<AuthRegisterRes>

    @PUT("v2/shop/update")
    fun updatePhone(@Header("Authorization") BearerToken: String, @Body req: UpdatePhoneShopReq):Call<UpdateRes>

    @POST("v2/shop/get/parcel")
    fun shop_get_parcel(@Header("Authorization") BearerToken: String,@Body req: ShopGetParcelReq):Call<MainSpGetParcelRes>

    @GET("v2/shop/id/automatic")
    fun autoId():Call<MainAutoIdRes>

    object Api {
        val api: ApiShopService by lazy { RetrofitSetting().retrofit.create(ApiShopService::class.java) }
    }

}