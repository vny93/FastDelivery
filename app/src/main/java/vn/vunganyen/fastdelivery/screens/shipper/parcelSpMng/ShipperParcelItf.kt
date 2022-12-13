package vn.vunganyen.fastdelivery.screens.shipper.parcelSpMng

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes

interface ShipperParcelItf {
    fun getListStatus(list: List<ListStatusRes>)
    fun getListParcel(list: List<SpGetParcelRes>)
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun getIdStatus(res : GetIdStatusRes, req2:SpGetParcelRes)
    fun addDetailStatus()
    fun wayExist(res : WarehouseRes, data : SpGetParcelRes)
    fun wayNotExist(data: SpGetParcelRes)
    fun updateWay(data: SpGetParcelRes)
    fun updatePaymentStatus(data: SpGetParcelRes)
   // fun updateCartStatus()
}