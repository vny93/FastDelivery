package vn.vunganyen.fastdelivery.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSetting2 {
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(PathURL.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}

