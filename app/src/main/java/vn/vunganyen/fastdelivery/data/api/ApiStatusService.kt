package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.staff.MainListStaffRes
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes


interface ApiStatusService {

    @GET("v2/status/get/list")
    fun getListStatus(@Header("Authorization") BearerToken: String):Call<MainListStatusRes>


    object Api {
        val api: ApiStatusService by lazy { RetrofitSetting().retrofit.create(ApiStatusService::class.java) }
    }

}