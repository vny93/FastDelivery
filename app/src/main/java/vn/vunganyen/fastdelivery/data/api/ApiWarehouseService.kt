package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes


interface ApiWarehouseService {

    @GET("v2/warehouse/get/list")
    fun getListWarehouse(@Header("Authorization") BearerToken: String):Call<MainWarehouseRes>


    object Api {
        val api: ApiWarehouseService by lazy { RetrofitSetting().retrofit.create(ApiWarehouseService::class.java) }
    }

}