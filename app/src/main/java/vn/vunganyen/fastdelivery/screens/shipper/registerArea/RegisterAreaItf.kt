package vn.vunganyen.fastdelivery.screens.shipper.registerArea

import vn.vunganyen.fastdelivery.data.model.area.GetListAreaRes

interface RegisterAreaItf {
    fun getListRegisterArea(list : List<GetListAreaRes>)
    fun getListArea(list: List<GetListAreaRes>)
    fun addSuccess()
    fun registerExist()
    fun Empty()
    fun updateSuccess()
}