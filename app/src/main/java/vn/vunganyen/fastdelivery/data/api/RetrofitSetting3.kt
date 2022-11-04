package vn.vunganyen.fastdelivery.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSetting3 {
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(PathURL.BASE_URL3)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}

