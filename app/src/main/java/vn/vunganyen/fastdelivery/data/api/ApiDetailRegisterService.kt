package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.area.MainGetListAreaRes
import vn.vunganyen.fastdelivery.data.model.detailRegister.*
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusReq
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusRes
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes
import vn.vunganyen.fastdelivery.data.model.staff.MainListStaffRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.MainGetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.MainListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.MainWarehouseRes


interface ApiDetailRegisterService {

    @POST("v2/detailRegister/get/list/areaShipper")
    fun getAreaShipper(@Header("Authorization") BearerToken: String, @Body req: GetAreaShipperReq):Call<MainGetListAreaRes>

    @POST("v2/detailRegister/check/exist")
    fun checkAreaShipper(@Header("Authorization") BearerToken: String, @Body req: InsertAreaReq):Call<CheckWordRes>

    @POST("v2/detailRegister/add")
    fun addAreaShipper(@Header("Authorization") BearerToken: String, @Body req: InsertAreaReq):Call<InsertAreaRes>

    @POST("v2/detailRegister/delete")
    fun deleteAreaShipper(@Header("Authorization") BearerToken: String, @Body req: InsertAreaReq):Call<UpdateRes>

    @POST("v2/detailRegister/update/status")
    fun updateAreaShipper(@Header("Authorization") BearerToken: String, @Body req: UpdateAreaShipperReq):Call<UpdateRes>


    object Api {
        val api: ApiDetailRegisterService by lazy { RetrofitSetting().retrofit.create(ApiDetailRegisterService::class.java) }
    }

}