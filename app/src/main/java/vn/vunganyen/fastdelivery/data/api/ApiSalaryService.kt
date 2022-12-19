package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics1Req
import vn.vunganyen.fastdelivery.data.model.salary.*


interface ApiSalaryService {

    @POST("v2/salary/shipper/get")
    fun shipper_get_salary(@Header("Authorization") BearerToken: String,@Body req: ShipperSalaryReq):Call<MainShipperSalaryRes>

    @POST("v2/salary/check")
    fun check_salary(@Header("Authorization") BearerToken: String,@Body req: CheckSalaryReq):Call<CheckSalaryRes>

    @POST("v2/salary/add")
    fun add_salary_staff(@Header("Authorization") BearerToken: String,@Body req: AddSalaryStaffReq):Call<UpdateRes>

    @POST("v2/salary/admin/collection")
    fun admin_collection(@Header("Authorization") BearerToken: String,@Body req: Statistics1Req):Call<MainCollectionRes>

    @POST("v2/salary/shipper/collection")
    fun shipper_collection(@Header("Authorization") BearerToken: String,@Body req: ShipperCollectionReq):Call<MainCollectionRes>

    @POST("v2/salary/add")
    fun add_salary_shipper(@Header("Authorization") BearerToken: String,@Body req: AddSalaryShipperReq):Call<UpdateRes>

    object Api {
        val api: ApiSalaryService by lazy { RetrofitSetting().retrofit.create(ApiSalaryService::class.java) }
    }

}