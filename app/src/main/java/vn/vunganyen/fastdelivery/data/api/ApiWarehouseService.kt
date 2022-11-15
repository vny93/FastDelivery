package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.data.model.warehouse.*


interface ApiWarehouseService {

    @GET("v2/warehouse/get/list")
    fun getListWarehouse(@Header("Authorization") BearerToken: String):Call<MainWarehouseRes>

    @POST("v2/warehouse/get/parcel/status")
    fun getParcelWh(@Header("Authorization") BearerToken: String, @Body req: GetParcelWhReq):Call<MainGetParcelWhRes>

    @POST("v2/warehouse/get/detail")
    fun getDetail(@Header("Authorization") BearerToken: String, @Body req: GetDetailWHReq):Call<GetDetailWHRes>

    @PUT("v2/warehouse/update")
    fun update(@Header("Authorization") BearerToken: String, @Body req: WarehouseRes):Call<UpdateRes>

    @POST("v2/warehouse/add")
    fun insert(@Header("Authorization") BearerToken: String, @Body req: InsertWHReq):Call<InsertWHRes>

    @POST("v2/warehouse/check/use")
    fun checkWHUser(@Header("Authorization") BearerToken: String, @Body req: GetDetailWHReq):Call<CheckWordRes>

    @POST("v2/warehouse/delete")
    fun delete(@Header("Authorization") BearerToken: String, @Body req: GetDetailWHReq):Call<UpdateRes>
    object Api {
        val api: ApiWarehouseService by lazy { RetrofitSetting().retrofit.create(ApiWarehouseService::class.java) }
    }

}