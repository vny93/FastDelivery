package vn.vunganyen.fastdelivery.screens.admin.shopMng.update

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes

interface UpdateShopMngItf {
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun EmailExist()
    fun UpdateSucces()
    fun lockAccount()
    fun activeAccount()
}