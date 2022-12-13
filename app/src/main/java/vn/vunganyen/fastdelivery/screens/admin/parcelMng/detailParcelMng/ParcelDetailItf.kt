package vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng

import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.FullStatusDetailRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes

interface ParcelDetailItf {
    fun getShopDetail(res : GetShopDetailRes)
    fun getDetailParcel(list : List<GetDetailParcelRes>)
    fun fullStatusDetail(list : List<FullStatusDetailRes>)
}