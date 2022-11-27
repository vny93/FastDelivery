package vn.vunganyen.fastdelivery.screens.staff.parcelDetail

import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes

interface StaffParcelDetailItf {
    fun getShopDetail(res : GetShopDetailRes)
    fun getDetailParcel(list : List<GetDetailParcelRes>)
}