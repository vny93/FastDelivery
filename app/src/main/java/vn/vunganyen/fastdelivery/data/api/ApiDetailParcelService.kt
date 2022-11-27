package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelReq
import vn.vunganyen.fastdelivery.data.model.detailParcel.MainGetDetailParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.*


interface ApiDetailParcelService {

    @POST("v2/detailParcel/get")
    fun get_detail_parcel(@Header("Authorization") BearerToken: String,@Body req: GetDetailParcelReq):Call<MainGetDetailParcelRes>

    object Api {
        val api: ApiDetailParcelService by lazy { RetrofitSetting().retrofit.create(ApiDetailParcelService::class.java) }
    }
}