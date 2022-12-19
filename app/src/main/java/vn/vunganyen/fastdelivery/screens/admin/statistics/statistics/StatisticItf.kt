package vn.vunganyen.fastdelivery.screens.admin.statistics.statistics

import vn.vunganyen.fastdelivery.data.model.parcel.Statistics1Res
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics2Res
import vn.vunganyen.fastdelivery.data.model.salary.CollectionRes

interface StatisticItf {
    fun getTt1(list : List<Statistics1Res>)
    fun success(list : List<Statistics1Res>)
    fun fail(list : List<Statistics1Res>)
    fun toReturn(list : List<Statistics1Res>)
    fun shipping(list : List<Statistics1Res>)
    fun get_list_collection(list: List<CollectionRes>)
}