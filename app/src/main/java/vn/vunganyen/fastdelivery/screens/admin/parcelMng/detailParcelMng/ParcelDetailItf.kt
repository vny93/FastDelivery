package vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng

import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes

interface ParcelDetailItf {
    fun getShopDetail(res : GetShopDetailRes)
}