package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.detailStatus.CountReq
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusReq
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusRes
import vn.vunganyen.fastdelivery.data.model.detailStatus.MainCountRes
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.staff.MainListStaffRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.MainGetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes


interface ApiStatusService {

    @GET("v2/status/get/list")
    fun getListStatus(@Header("Authorization") BearerToken: String):Call<MainListStatusRes>

    @POST("v2/detailStatus/add")
    fun addDetailStatus(@Header("Authorization") BearerToken: String, @Body req: DetailStatusReq):Call<DetailStatusRes>

    @POST("v2/status/get/id")
    fun getIdStatus(@Header("Authorization") BearerToken: String, @Body req: GetIdStatusReq):Call<MainGetIdStatusRes>

    @POST("v2/detailStatus/count")
    fun count(@Header("Authorization") BearerToken: String, @Body req: CountReq):Call<MainCountRes>

    object Api {
        val api: ApiStatusService by lazy { RetrofitSetting().retrofit.create(ApiStatusService::class.java) }
    }

}