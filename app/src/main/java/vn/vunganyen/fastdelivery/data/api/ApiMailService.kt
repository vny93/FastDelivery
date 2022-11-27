package vn.vunganyen.fastdelivery.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.fastdelivery.data.model.mail.MailReq
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes


interface ApiMailService {

    @POST("v2/mail/send")
    fun sendMail(@Body req: MailReq): Call<UpdateRes>

    object Api {
        val api: ApiMailService by lazy { RetrofitSetting().retrofit.create(ApiMailService::class.java) }
    }

}