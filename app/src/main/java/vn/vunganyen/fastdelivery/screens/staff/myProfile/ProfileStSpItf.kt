package vn.vunganyen.fastdelivery.screens.staff.myProfile

import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes

interface ProfileStSpItf {
    fun getRoleDetail(res : ListRoleRes)
    fun getDetailWH(res : WarehouseRes)
    fun Empty()
    fun PhoneIllegal()
    fun PhoneExist()
    fun updateSuccess()
    fun CheckSuccess()
}