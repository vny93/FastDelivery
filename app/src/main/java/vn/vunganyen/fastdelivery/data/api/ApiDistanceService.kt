package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.distance.DistanceUPriceReq
import vn.vunganyen.fastdelivery.data.model.distance.MainDistanceRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes


interface ApiDistanceService {

    @GET("v2/distance/get_list")
    fun getList(@Header("Authorization") BearerToken: String):Call<MainDistanceRes>

    @PUT("v2/distance/update")
    fun updateDistancePrice(@Header("Authorization") BearerToken: String,@Body req: DistanceUPriceReq):Call<UpdateRes>

    object Api {
        val api: ApiDistanceService by lazy { RetrofitSetting().retrofit.create(ApiDistanceService::class.java) }
    }

}