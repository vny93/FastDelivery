package vn.vunganyen.fastdelivery.screens.admin.shopMng.getList

import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes

interface ShopMngItf {
    fun getListShop(list : List<GetShopDetailRes>)
}