package vn.vunganyen.fastdelivery.screens.admin.staffMng.getList

import vn.vunganyen.fastdelivery.data.model.staff.CheckWorkReq
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes

interface StaffMngItf {
    fun getFullStaff(list : List<ListStaffRes>)
    fun deleteFail()
    fun allowDetele(req : CheckWorkReq)
    fun deleteSuccess()
}