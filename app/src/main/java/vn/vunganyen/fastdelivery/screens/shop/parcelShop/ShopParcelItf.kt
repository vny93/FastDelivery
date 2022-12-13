package vn.vunganyen.fastdelivery.screens.shop.parcelShop

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes

interface ShopParcelItf {
    fun getListStatus(list: List<ListStatusRes>)
    fun getListParcel(list: List<SpGetParcelRes>)
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
}