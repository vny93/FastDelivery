package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import vn.vunganyen.fastdelivery.data.model.area.MainGetListAreaRes

interface ApiAreaService {
    @GET("v2/area/get/list")
    fun getListArea(@Header("Authorization") BearerToken: String): Call<MainGetListAreaRes>

    object Api {
        val api: ApiAreaService by lazy { RetrofitSetting().retrofit.create(ApiAreaService::class.java) }
    }
}