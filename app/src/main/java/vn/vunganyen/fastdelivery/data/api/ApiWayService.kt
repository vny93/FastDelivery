package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.CheckWayExistReq
import vn.vunganyen.fastdelivery.data.model.way.WayReq
import vn.vunganyen.fastdelivery.data.model.way.WayRes


interface ApiWayService {

    @POST("v2/way/add")
    fun addWay(@Header("Authorization") BearerToken: String, @Body req: WayReq):Call<WayRes>

    @POST("v2/way/check/exist")
    fun checkWayExist(@Header("Authorization") BearerToken: String, @Body req: CheckWayExistReq):Call<CheckWordRes>

    object Api {
        val api: ApiWayService by lazy { RetrofitSetting().retrofit.create(ApiWayService::class.java) }
    }

}