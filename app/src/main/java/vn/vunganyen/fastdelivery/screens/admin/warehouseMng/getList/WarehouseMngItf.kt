package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.getList

import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes

interface WarehouseMngItf {
    fun getListWarehouse(list : List<WarehouseRes>)
    fun deleteFail()
    fun allowDetele(req : GetDetailWHReq)
    fun deleteSuccess()
}