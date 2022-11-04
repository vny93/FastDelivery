package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes


interface ApiMassService {

    @GET("v2/mass/get_list")
    fun getList(@Header("Authorization") BearerToken: String):Call<MainMassRes>

    @PUT("v2/mass/update")
    fun updateMassPrice(@Header("Authorization") BearerToken: String,@Body req: MassUPriceReq):Call<UpdateRes>

    object Api {
        val api: ApiMassService by lazy { RetrofitSetting().retrofit.create(ApiMassService::class.java) }
    }

}