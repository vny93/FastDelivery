package vn.vunganyen.fastdelivery.screens.admin.priceList

import vn.vunganyen.fastdelivery.data.model.distance.DistanceRes
import vn.vunganyen.fastdelivery.data.model.mass.MassRes

interface PriceItf {
    fun getListDistance(list : List<DistanceRes>)
    fun getListMass(list : List<MassRes>)
    fun updateMassPrice()
    fun updateDistancePrice()
    fun deleteMass()
    fun deleteDistance()
    fun massError(max : Int)
    fun distanceErorr(max : Int)
    fun addDistance()
    fun addMass()
    fun comtopareMassError()
    fun comtopareDistanceError()
    fun priceError()
}