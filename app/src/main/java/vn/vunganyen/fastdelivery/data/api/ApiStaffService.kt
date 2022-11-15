package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.data.model.staff.*


interface ApiStaffService {

    @POST("v2/staff/get/profile")
    fun getProfile(@Header("Authorization") BearerToken: String, @Body req: ProfileReq): Call<MainProfileRes>

    @POST("v2/staff/get/list")
    fun getListStaff(@Header("Authorization") BearerToken: String, @Body req: ListStaffReq): Call<MainListStaffRes>

    @GET("v2/staff/get/full")
    fun getFullStaff(@Header("Authorization") BearerToken: String): Call<MainListStaffRes>

    @PUT("v2/staff/admin/update")
    fun adminUpdateStaff(@Header("Authorization") BearerToken: String, @Body req: AdminUpdateReq): Call<UpdateRes>

    @PUT("v2/staff/update")
    fun staffUpdate(@Header("Authorization") BearerToken: String, @Body req: StaffUpdateReq): Call<UpdateRes>

    @POST("v2/staff/register")
    fun insertStaff(@Header("Authorization") BearerToken: String, @Body req: ProfileRes): Call<MainProfileRes>

    @POST("v2/staff/register")
    fun insertShipper(@Header("Authorization") BearerToken: String, @Body req: InsertShipperReq): Call<MainProfileRes>

    @POST("v2/staff/check/work")
    fun checkStaffWord(@Header("Authorization") BearerToken: String, @Body req: CheckWorkReq): Call<CheckWordRes>

    @POST("v2/staff/delete")
    fun deleteStaff(@Header("Authorization") BearerToken: String, @Body req: CheckWorkReq): Call<UpdateRes>

    @POST("v2/staff/id/automatic")
    fun automaticId(@Header("Authorization") BearerToken: String, @Body req: AutomaticIdReq): Call<MainAutomaticIdRes>

    @POST("v2/staff/get/detail")
    fun checkStaffExist(@Header("Authorization") BearerToken: String, @Body req: CheckWorkReq): Call<MainCheckStaffRes>

    @POST("v2/staff/checkPhone")
    fun checkPhone(@Header("Authorization") BearerToken: String, @Body req: CheckPhoneReq): Call<CheckProfileRes>

    @POST("v2/staff/checkEmail")
    fun checkEmail(@Header("Authorization") BearerToken: String, @Body req: CheckEmailReq): Call<CheckProfileRes>

    @POST("v2/staff/checkCmnd")
    fun checkCmnd(@Header("Authorization") BearerToken: String, @Body req: CheckCmndReq): Call<CheckProfileRes>

    @POST("v2/staff/get/shipper/area")
    fun getShipperArea(@Header("Authorization") BearerToken: String, @Body req: ShipperAreaReq): Call<MainShipperAreaRes>

    object Api {
        val api: ApiStaffService by lazy { RetrofitSetting().retrofit.create(ApiStaffService::class.java) }
    }

}