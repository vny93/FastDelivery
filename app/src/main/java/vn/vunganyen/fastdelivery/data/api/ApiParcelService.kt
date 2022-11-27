package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.parcel.*
import vn.vunganyen.fastdelivery.data.model.turnover.MainTurnoverRes
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverReq


interface ApiParcelService {

    @POST("v2/parcel/admin/get")
    fun admin_get_parcel(@Header("Authorization") BearerToken: String,@Body req: AdGetParcelReq):Call<MainAdGetParcelRes>

    @POST("v2/parcel/staff/get")
    fun staff_get_parcel(@Header("Authorization") BearerToken: String,@Body req: StGetParcelReq):Call<MainStaffParcelRes>

    @POST("v2/parcel/shipper/get")
    fun shipper_get_parcel(@Header("Authorization") BearerToken: String,@Body req: SpGetParcelReq):Call<MainSpGetParcelRes>

    @PUT("v2/parcel/update/paymentStatus")
    fun update_payment_status(@Header("Authorization") BearerToken: String,@Body req: UpdatePaymentStatusReq):Call<UpdateRes>

    @POST("v2/parcel/admin/statistics1")
    fun admin_statistics1(@Header("Authorization") BearerToken: String,@Body req: Statistics1Req):Call<MainStatistics1Res>

    @POST("v2/parcel/admin/statistics2")
    fun admin_statistics2(@Header("Authorization") BearerToken: String,@Body req: Statistics2Req):Call<MainStatistics2Res>

    @POST("v2/parcel/admin/turnover")
    fun admin_turnover(@Header("Authorization") BearerToken: String,@Body req: TurnoverReq):Call<MainTurnoverRes>

    object Api {
        val api: ApiParcelService by lazy { RetrofitSetting().retrofit.create(ApiParcelService::class.java) }
    }

}