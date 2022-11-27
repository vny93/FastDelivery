package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.salary.MainShipperSalaryRes
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryReq


interface ApiSalaryService {

    @POST("v2/salary/shipper/get")
    fun shipper_get_salary(@Header("Authorization") BearerToken: String,@Body req: ShipperSalaryReq):Call<MainShipperSalaryRes>

    object Api {
        val api: ApiSalaryService by lazy { RetrofitSetting().retrofit.create(ApiSalaryService::class.java) }
    }

}