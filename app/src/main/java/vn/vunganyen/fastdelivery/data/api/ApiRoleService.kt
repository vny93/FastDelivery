package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mass.MainMassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes


interface ApiRoleService {

    @GET("v2/role/get/list")
    fun getListRole(@Header("Authorization") BearerToken: String):Call<MainListRoleRes>


    object Api {
        val api: ApiRoleService by lazy { RetrofitSetting().retrofit.create(ApiRoleService::class.java) }
    }

}