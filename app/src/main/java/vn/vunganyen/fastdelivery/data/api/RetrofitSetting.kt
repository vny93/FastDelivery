package vn.vunganyen.fastdelivery.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSetting {
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(PathURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}

