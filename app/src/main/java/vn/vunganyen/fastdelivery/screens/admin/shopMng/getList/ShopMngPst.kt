package vn.vunganyen.fastdelivery.screens.admin.shopMng.getList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.ApiShopService
import vn.vunganyen.fastdelivery.data.model.shop.GetListShopRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ShopMngPst {
    var shopMngItf : ShopMngItf

    constructor(shopMngItf: ShopMngItf) {
        this.shopMngItf = shopMngItf
    }

    fun getListShop(){
        ApiShopService.Api.api.getListShop(SplashActivity.token).enqueue(object : Callback<GetListShopRes>{
            override fun onResponse(call: Call<GetListShopRes>, response: Response<GetListShopRes>) {
                if(response.isSuccessful){
                    ShopMngActivity.listShop = response.body()!!.result as ArrayList<GetShopDetailRes>
                    shopMngItf.getListShop(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<GetListShopRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getFilter(s : String){
        ShopMngActivity.listFilter = ArrayList<GetShopDetailRes>()
        for(list in ShopMngActivity.listShop){
            if(list.mach.toUpperCase().contains(s.toUpperCase())){
                ShopMngActivity.listFilter.add(list)
            }
        }
        shopMngItf.getListShop(ShopMngActivity.listFilter)
    }

}