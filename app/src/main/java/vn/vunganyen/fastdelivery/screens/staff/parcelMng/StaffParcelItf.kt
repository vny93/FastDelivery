package vn.vunganyen.fastdelivery.screens.staff.parcelMng

import vn.vunganyen.fastdelivery.data.model.detailStatus.CountRes
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.CancelInforRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.StaffGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes

interface StaffParcelItf {
    fun getListStatus(list: List<ListStatusRes>)
    fun getListParcel(list: List<StaffGetParcelRes>)
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun getWarehouseDetail(req : WarehouseRes)
    fun getIdStatus(res : GetIdStatusRes, req2:StaffGetParcelRes)
    fun addDetailStatus()
    fun getShipperArea(list : List<ShipperAreaRes>, status : String)
    fun getShopDetail(res : GetShopDetailRes)
    fun wayExist(res : WarehouseRes, data : StaffGetParcelRes)
    fun wayNotExist(data: StaffGetParcelRes)
    fun count(res : CountRes, data : StaffGetParcelRes)
    fun cancelInfor(res : CancelInforRes)
}