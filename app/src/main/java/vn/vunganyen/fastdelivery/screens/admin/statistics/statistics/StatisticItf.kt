package vn.vunganyen.fastdelivery.screens.admin.statistics.statistics

import vn.vunganyen.fastdelivery.data.model.parcel.Statistics1Res
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics2Res

interface StatisticItf {
    fun getTt1(list : List<Statistics1Res>)
    fun getTt2(list : List<Statistics2Res>)
    fun getTt3(list : List<Statistics2Res>)
    fun getTt4(list : List<Statistics2Res>)
}