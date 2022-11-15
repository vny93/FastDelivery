package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.insert

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes

interface InsertWarehouseItf {
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun Empty()
    fun AddSucces()
}