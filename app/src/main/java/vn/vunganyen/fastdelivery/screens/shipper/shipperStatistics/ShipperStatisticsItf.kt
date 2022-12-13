package vn.vunganyen.fastdelivery.screens.shipper.shipperStatistics

import vn.vunganyen.fastdelivery.data.model.parcel.ShipperStatisticsRes

interface ShipperStatisticsItf {
    fun getListSuccess(list : List<ShipperStatisticsRes>)
}