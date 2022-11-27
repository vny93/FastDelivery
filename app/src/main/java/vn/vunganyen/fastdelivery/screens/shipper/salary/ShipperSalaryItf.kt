package vn.vunganyen.fastdelivery.screens.shipper.salary

import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryRes

interface ShipperSalaryItf {
    fun shipper_get_salary(list : List<ShipperSalaryRes>)
}