package vn.vunganyen.fastdelivery.screens.admin.statistics.statistics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics1Req
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics1Res
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics2Req
import vn.vunganyen.fastdelivery.data.model.parcel.Statistics2Res
import vn.vunganyen.fastdelivery.databinding.ActivityStatisticsBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class StatisticsActivity : AppCompatActivity(), StatisticItf {
    lateinit var binding : ActivityStatisticsBinding
    lateinit var statisticsPst: StatisticsPst
    var listChart = ArrayList<Int>()
    var listNameChart = listOf("Vận chuyễn","Thành công","Thất bại","Hoàn trả")
    companion object{
        var tt1 = "Hoàn tất" //"Giao hàng thành công"
        var tt2 = "Giao hàng thất bại"
        var tt3 = "Hoàn tất hoàn trả" //"Hoàn trả"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statisticsPst = StatisticsPst(this)
        setToolbar()
        setData()
    }

    fun setData(){
        var dateFrom = getIntent().getStringExtra("dateFrom")
        var dateTo = getIntent().getStringExtra("dateTo")
        println("dateFrom: "+dateFrom)
        println("dateTo: "+dateTo)
        var mdate: Date = SplashActivity.formatdate.parse(dateFrom)
        var strDate = SplashActivity.formatdate1.format(mdate)
        binding.tvDateFrom.setText(strDate)
        var mdate2: Date = SplashActivity.formatdate.parse(dateTo)
        var strDate2 = SplashActivity.formatdate1.format(mdate2)
        binding.tvDateTo.setText(strDate2)

        var req1 = Statistics1Req(dateFrom!!, dateTo!!)
        var req2 = Statistics2Req(dateFrom!!, dateTo!!,tt1)
        var req3 = Statistics2Req(dateFrom!!, dateTo!!,tt2)
        var req4 = Statistics2Req(dateFrom!!, dateTo!!,tt3)
        //nhận từ cửa hàng
        statisticsPst.getStatistics1(req1)
        //số vận đơn
        statisticsPst.getStatistics3(req1)
        //giao thành công
        statisticsPst.getStatistics2(req2)
        //giao thất bại
        statisticsPst.getStatistics2(req3)
        //hoàn trả
        statisticsPst.getStatistics2(req4)
    }

    fun setToolbar() {
        var toolbar = binding.toolbarAdminParcel
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getTt1(list: List<Statistics1Res>) {
        binding.tvStt1.setText(list.get(0).sl.toString())
        //listChart.add(list.get(0).sl)
    }

    override fun success(list: List<Statistics1Res>) {
//        println("vô nè: "+list.size)
//        var temp = 0
//        if(list.size > 0){
//            binding.tvStt2.setText((list.size).toString())
//            temp = list.size
//        }
//        else binding.tvStt2.setText("0")
//        listChart.add(temp)
        binding.tvStt2.setText(list.get(0).sl.toString())
        listChart.add(list.get(0).sl)
    }

    override fun fail(list: List<Statistics1Res>) {
//        println("vô nè: "+list.size)
//        var temp = 0
//        if(list.size > 0){
//            binding.tvStt3.setText((list.size).toString())
//            temp = list.size
//        }
//        else binding.tvStt3.setText("0")
//        listChart.add(temp)
        binding.tvStt3.setText(list.get(0).sl.toString())
        listChart.add(list.get(0).sl)
    }

    override fun toReturn(list: List<Statistics1Res>) {
//        println("vô nè: "+list.size)
//        var temp = 0
//        if(list.size > 0){
//            binding.tvStt4.setText((list.size).toString())
//            temp = list.size
//        }
//        else binding.tvStt4.setText("0")
//        listChart.add(temp)
        binding.tvStt4.setText(list.get(0).sl.toString())
        listChart.add(list.get(0).sl)

        //call chart
        Handler().postDelayed({
            configChartView()
        }, 1500)
    }

    override fun shipping(list: List<Statistics1Res>) {
        binding.tvStt5.setText(list.get(0).sl.toString())
        listChart.add(list.get(0).sl)
    }

    fun configChartView(){
        var pie = AnyChart.pie()
        var dataPieChart : MutableList<DataEntry> = mutableListOf()
        for(index in listChart.indices){
            dataPieChart.add(ValueDataEntry(listNameChart.elementAt(index),listChart.elementAt(index)))
        }
        pie.data(dataPieChart)
//        pie.title("Biểu đồ tròn")
        binding.pieChart.setChart(pie)
    }
}