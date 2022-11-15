package vn.vunganyen.fastdelivery.screens.shipper.parcelSpMng

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes

interface ShipperParcelItf {
    fun getListStatus(list: List<ListStatusRes>)
    fun getListParcel(list: List<StGetParcelRes>)
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun getIdStatus(res : GetIdStatusRes, req2:StGetParcelRes)
    fun addDetailStatus()
    fun checkWayExist(res : Boolean, data : StGetParcelRes)
}