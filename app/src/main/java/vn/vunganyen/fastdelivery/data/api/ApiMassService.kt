package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.distance.MainGetMaxRes
import vn.vunganyen.fastdelivery.data.model.mass.*


interface ApiMassService {

    @GET("v2/mass/get_list")
    fun getList(@Header("Authorization") BearerToken: String):Call<MainMassRes>

    @GET("v2/mass/get/max")
    fun getMaxMass(@Header("Authorization") BearerToken: String):Call<MainGetMaxRes>

    @PUT("v2/mass/update")
    fun updateMassPrice(@Header("Authorization") BearerToken: String,@Body req: MassUPriceReq):Call<UpdateRes>

    @POST("v2/mass/delete")
    fun deleteMass(@Header("Authorization") BearerToken: String,@Body req: DeleteMassReq):Call<UpdateRes>

    @POST("v2/mass/add")
    fun addMass(@Header("Authorization") BearerToken: String,@Body req: AddMassReq):Call<UpdateRes>

    object Api {
        val api: ApiMassService by lazy { RetrofitSetting().retrofit.create(ApiMassService::class.java) }
    }

}