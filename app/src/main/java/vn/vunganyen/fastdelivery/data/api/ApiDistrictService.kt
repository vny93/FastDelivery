package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes


interface ApiDistrictService {

    @GET("p/79")
    fun getDistrict1():Call<MainGetDistrictRes>

    @GET("d/{code}")
    fun getDistrict2(@Path("code") code : Long):Call<DistrictRes>


    object Api {
        val api: ApiDistrictService by lazy { RetrofitSetting3().retrofit.create(ApiDistrictService::class.java) }
    }

}