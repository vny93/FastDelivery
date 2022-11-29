package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.distance.*
import vn.vunganyen.fastdelivery.data.model.mass.DeleteMassReq
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes


interface ApiDistanceService {

    @GET("v2/distance/get_list")
    fun getList(@Header("Authorization") BearerToken: String):Call<MainDistanceRes>

    @GET("v2/distance/get/max")
    fun getMaxDistance(@Header("Authorization") BearerToken: String):Call<MainGetMaxRes>

    @PUT("v2/distance/update")
    fun updateDistancePrice(@Header("Authorization") BearerToken: String,@Body req: DistanceUPriceReq):Call<UpdateRes>

    @POST("v2/distance/delete")
    fun deleteDistance(@Header("Authorization") BearerToken: String,@Body req: DeleteDistanceReq):Call<UpdateRes>

    @POST("v2/distance/add")
    fun addDistance(@Header("Authorization") BearerToken: String,@Body req: AddDistanceReq):Call<UpdateRes>


    object Api {
        val api: ApiDistanceService by lazy { RetrofitSetting().retrofit.create(ApiDistanceService::class.java) }
    }

}