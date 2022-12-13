package vn.vunganyen.fastdelivery.screens.shipper.map

import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes

interface MapItf {
    fun getShopDetail(res : GetShopDetailRes)
    fun getEncoded(encoded : String)
    fun getListPoint(list : List<List<Double>>)
}