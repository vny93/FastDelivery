package vn.vunganyen.fastdelivery.screens.shop.registerShop

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes

interface ShopRegisterItf {
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
    fun Empty()
    fun UserIllegal()
    fun PassIllegal()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun AccountExist()
    fun PhoneExist()
    fun EmailExist()
    fun CheckSuccess()
    fun AutomaticId(id : String)
    fun AddSucces()
}