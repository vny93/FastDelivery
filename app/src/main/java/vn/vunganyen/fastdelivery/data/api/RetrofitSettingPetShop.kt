package vn.vunganyen.fastdelivery.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSettingPetShop {
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(PathURL.BASE_URL_petShop)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}

