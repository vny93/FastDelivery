package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.distance.DistanceUPriceReq
import vn.vunganyen.fastdelivery.data.model.distance.MainDistanceRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.parcel.*


interface ApiParcelService {

    @POST("v2/parcel/admin/get")
    fun admin_get_parcel(@Header("Authorization") BearerToken: String,@Body req: AdGetParcelReq):Call<MainAdGetParcelRes>

    @POST("v2/parcel/staff/get")
    fun staff_get_parcel(@Header("Authorization") BearerToken: String,@Body req: StGetParcelReq):Call<MainStGetParcelRes>

    @POST("v2/parcel/shipper/get")
    fun shipper_get_parcel(@Header("Authorization") BearerToken: String,@Body req: SpGetParcelReq):Call<MainStGetParcelRes>

    object Api {
        val api: ApiParcelService by lazy { RetrofitSetting().retrofit.create(ApiParcelService::class.java) }
    }

}