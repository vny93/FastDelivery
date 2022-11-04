package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.graphhopper.GraphhopperRes
import vn.vunganyen.fastdelivery.data.model.graphhopper.PointReq


interface GraphhopperService {

    @GET(".")
    fun getDistanceApi(@Query("point") pointSource : String,
                @Query("point") pointDes : String,
                @Query("profile") profile : String,
                @Query("locale") locale : String,
                @Query("calc_points") calc_points : Boolean,
                @Query("key") key : String):Call<GraphhopperRes>
    object Api {
        val api: GraphhopperService by lazy { RetrofitSetting2().retrofit.create(GraphhopperService::class.java) }
    }

}