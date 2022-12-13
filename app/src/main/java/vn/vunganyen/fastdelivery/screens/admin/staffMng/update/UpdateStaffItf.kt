package vn.vunganyen.fastdelivery.screens.admin.staffMng.update

import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.ShipperStatisticsRes
import vn.vunganyen.fastdelivery.data.model.salary.AddSalaryShipperReq
import vn.vunganyen.fastdelivery.data.model.salary.AddSalaryStaffReq
import vn.vunganyen.fastdelivery.data.model.salary.CheckSalaryReq
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryRes

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
    fun lockAccount()
    fun activeAccount()
    fun checkSalaryExist(strDate : String)
    fun checkSalaryNotExist()
    fun getListSuccess(list : List<ShipperStatisticsRes>)
    fun addSalaryStaff(strDate : String)
    fun addSalaryShipper(strDate : String)

}