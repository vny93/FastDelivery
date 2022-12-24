package vn.vunganyen.fastdelivery.screens.shipper.barcode

import vn.vunganyen.fastdelivery.data.model.parcel.GetDetailParcelRes

interface ScanBarCodeItf {
    fun getDetailParcel(res : GetDetailParcelRes)
}