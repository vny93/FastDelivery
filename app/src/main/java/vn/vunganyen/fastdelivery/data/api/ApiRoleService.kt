package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.role.RoleReq
import vn.vunganyen.fastdelivery.data.model.role.RoleRes


interface ApiRoleService {

    @GET("v2/role/get/list")
    fun getListRole(@Header("Authorization") BearerToken: String):Call<MainListRoleRes>

    @POST("v2/role/get/byAuth")
    fun getRoleDetail(@Header("Authorization") BearerToken: String,@Body req: RoleReq):Call<MainListRoleRes>


    object Api {
        val api: ApiRoleService by lazy { RetrofitSetting().retrofit.create(ApiRoleService::class.java) }
    }

}