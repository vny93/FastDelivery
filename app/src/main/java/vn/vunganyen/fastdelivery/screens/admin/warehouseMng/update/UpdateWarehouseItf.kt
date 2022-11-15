package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.update

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes

interface UpdateWarehouseItf {
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun Empty()
    fun UpdateSucces()
}