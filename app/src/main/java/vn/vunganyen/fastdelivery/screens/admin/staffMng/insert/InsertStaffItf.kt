package vn.vunganyen.fastdelivery.screens.admin.staffMng.insert

import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes

interface InsertStaffItf {
    fun getRoleSuccess(list : List<ListRoleRes>)
    fun getWarehouseSuccess(list : List<WarehouseRes>)
    fun Empty()
    fun CmndIllegal()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun CmndExist()
    fun PhoneExist()
    fun EmailExist()
    fun AddSucces()
    fun AccountExist()
    fun UserIllegal()
    fun PassIllegal()
    fun CheckSuccess()
    fun OrlError()
    fun AutomaticId(id : String)
}