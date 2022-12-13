package vn.vunganyen.fastdelivery.screens.admin.statistics.barChart

import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes

interface AdminBarChartItf {
    fun getTurnover(list: List<TurnoverRes>)
}