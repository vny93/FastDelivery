package vn.vunganyen.fastdelivery.screens.admin.staffMng.update

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes

interface UpdateStaffItf {
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
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
}