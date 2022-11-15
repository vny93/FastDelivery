package vn.vunganyen.fastdelivery.screens.admin.myProfile

import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.role.RoleRes

interface AdminMyProfileItf {
    fun getRoleDetail(res : ListRoleRes)
    fun Empty()
    fun CmndIllegal()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun OrlError()
    fun CmndExist()
    fun PhoneExist()
    fun EmailExist()
    fun updateSuccess()
    fun CheckSuccess()
}