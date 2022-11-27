package vn.vunganyen.fastdelivery.screens.admin.statistics.turnover

import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes

interface TurnoverItf {
    fun getTurnover(list: List<TurnoverRes>)
    fun Empty()
    fun DateError()
}