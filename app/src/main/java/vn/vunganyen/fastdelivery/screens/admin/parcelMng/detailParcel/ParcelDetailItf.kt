package vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcel

import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes

interface ParcelDetailItf {
    fun getShopDetail(res : GetShopDetailRes)
}