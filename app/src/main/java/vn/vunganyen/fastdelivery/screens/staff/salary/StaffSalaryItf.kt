package vn.vunganyen.fastdelivery.screens.staff.salary

import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryRes

interface StaffSalaryItf {
    fun staff_get_salary(list : List<ShipperSalaryRes>)
}