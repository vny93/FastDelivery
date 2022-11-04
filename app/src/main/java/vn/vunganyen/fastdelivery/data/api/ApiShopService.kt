package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.shop.MainGetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes


interface ApiShopService {

    @POST("v2/shop/get/detail")
    fun getShopDetail(@Header("Authorization") BearerToken: String, @Body req: GetShopDetailReq):Call<MainGetShopDetailRes>


    object Api {
        val api: ApiShopService by lazy { RetrofitSetting().retrofit.create(ApiShopService::class.java) }
    }

}