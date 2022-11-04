package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterRes
import vn.vunganyen.fastdelivery.data.model.auth.ChangePwReq
import vn.vunganyen.fastdelivery.data.model.auth.UpdateStatusAuthReq
import vn.vunganyen.fastdelivery.data.model.login.LoginReq
import vn.vunganyen.fastdelivery.data.model.login.LoginRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.staff.CheckWordRes


interface ApiAuthService {

    @POST("v2/auth/login")
    fun authLogin(@Body login: LoginReq):Call<LoginRes>

    @POST("v2/auth/admin/register")
    fun registerAccount(@Header("Authorization") BearerToken: String, @Body req: AuthRegisterReq): Call<AuthRegisterRes>

    @PUT("v2/auth/update/password")
    fun changePassword(@Header("Authorization") BearerToken: String, @Body req: ChangePwReq): Call<UpdateRes>

    @PUT("v2/auth/update/status")
    fun updateStatusAuth(@Header("Authorization") BearerToken: String, @Body req: UpdateStatusAuthReq): Call<UpdateRes>

    @POST("v2/auth/findAccount")
    fun findAuth(@Body req: ProfileReq):Call<CheckWordRes>

    object Api {
        val api: ApiAuthService by lazy { RetrofitSetting().retrofit.create(ApiAuthService::class.java) }
    }

}