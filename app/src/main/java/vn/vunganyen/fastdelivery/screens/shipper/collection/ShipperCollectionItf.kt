package vn.vunganyen.fastdelivery.screens.shipper.collection

import vn.vunganyen.fastdelivery.data.model.salary.CollectionRes
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes

interface ShipperCollectionItf {
    fun getListCollection(list: List<CollectionRes>)
    fun Empty()
    fun DateError()
}