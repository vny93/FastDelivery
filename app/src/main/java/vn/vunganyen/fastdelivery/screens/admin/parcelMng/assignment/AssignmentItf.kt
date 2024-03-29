package vn.vunganyen.fastdelivery.screens.admin.parcelMng.assignment

import android.graphics.Bitmap
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhRes

interface AssignmentItf {
    fun getListStatus(list: List<ListStatusRes>)
    fun getListParcel(list: List<AdGetParcelRes>)
    fun getListWarehouse()
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun getShopDetail(res : GetShopDetailRes)
    fun addWaySuccess()
    fun getParcelWh(list : List<GetParcelWhRes>)
    fun bitmap(bitmap: Bitmap)
}