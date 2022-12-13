package vn.vunganyen.fastdelivery.screens.shipper.shipperStatistics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.data.adapter.AdapterShipperStatistics
import vn.vunganyen.fastdelivery.data.model.parcel.ShipperStatisticsReq
import vn.vunganyen.fastdelivery.data.model.parcel.ShipperStatisticsRes
import vn.vunganyen.fastdelivery.databinding.ActivityShipperStatisticsBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity.Companion.formatdate
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity.Companion.formatdate4
import java.util.*
import kotlin.collections.ArrayList

class ShipperStatisticsActivity : AppCompatActivity(),ShipperStatisticsItf {
    lateinit var binding : ActivityShipperStatisticsBinding
    lateinit var shipperStatisticPst: ShipperStatisticPst
    var adapter : AdapterShipperStatistics = AdapterShipperStatistics()
    var day = GregorianCalendar(TimeZone.getTimeZone("GMT+7")).get(Calendar.DAY_OF_MONTH)
    var year = GregorianCalendar(TimeZone.getTimeZone("GMT+7")).get(Calendar.YEAR)
    var month =GregorianCalendar(TimeZone.getTimeZone("GMT+7")).get(Calendar.MONTH)
    var calStartMonth = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    var calEndMonth = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    var cMonth =  GregorianCalendar(TimeZone.getTimeZone("GMT+7"))

    var dateFrom = ""
    var dateTo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipperStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shipperStatisticPst = ShipperStatisticPst(this)
        setToolbar()
        setData()
    }

    fun setData() {
        var date = getIntent().getStringExtra("date")
        val mdateFrom: Date = SplashActivity.formatdate2.parse(date)
        cMonth.clear()
        cMonth.time = mdateFrom

    //    cMonth.add(Calendar.HOUR_OF_DAY, 7)
        cMonth.roll(Calendar.MONTH,-1)
        var m_month = SplashActivity.formatMonth.format(cMonth.time)
        var m_year = SplashActivity.formatYear.format(mdateFrom)


        day = 1
        month = m_month.toString().toInt() //lọc tháng trước tháng nhận đc lương
        year = m_year.toString().toInt()

        println("day: "+day)
        println("month ne: "+month)
        println("year: "+year)

        calStartMonth.clear()
        calStartMonth.set(year, month,day)
        calStartMonth.roll(Calendar.MONTH,-1)
        dateFrom = formatdate.format(calStartMonth.time)+" 00:00:00"
        println("start month: "+ formatdate4.format(calStartMonth.time))

        calEndMonth.clear()
        calEndMonth.set(year, month,day)
        calEndMonth.roll(Calendar.MONTH,-1)
        // ngày cuối của tháng hiện tại
        shipperStatisticPst.findEndOfMonth(calEndMonth)
        dateTo = formatdate.format(calEndMonth.time)+" 23:59:59"
        println("end month: "+ formatdate4.format(calEndMonth.time))
        binding.tvDateFrom.setText(formatdate4.format(calStartMonth.time))
        binding.tvDateTo.setText(formatdate4.format(calEndMonth.time))

        var idShipper = SplashActivity.profile.result.manv
        var req = ShipperStatisticsReq(idShipper,dateFrom!!,dateTo!!)
        shipperStatisticPst.shipperStatistics(req)


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

//    override fun pickSuccess(list: List<ShipperStatisticsRes>) {
//        for(i in 0..list.size-1){
//            listAll.add(list.get(i))
//        }
//    }
//
//    override fun deleveriSuccess(list: List<ShipperStatisticsRes>) {
//        for(i in 0..list.size-1){
//            listAll.add(list.get(i))
//        }
//    }
//
//    override fun returnSuccess(list: List<ShipperStatisticsRes>) {
//        for(i in 0..list.size-1){
//            listAll.add(list.get(i))
//        }
//        setAdapter(listAll)
//    }

    override fun getListSuccess(list: List<ShipperStatisticsRes>) {
        setAdapter(list)
    }

    fun setAdapter(list : List<ShipperStatisticsRes>){
        var sum = 0.0f
        for(i in 0..list.size-1){
            sum = sum + list.get(i).phigiao
        }
        adapter.setData(list)
        binding.rcvListStt.adapter = adapter
        binding.rcvListStt.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.tvPercent.setText(SplashActivity.PERCENT.toString()+"%")
        val price = SplashActivity.formatterPrice.format(sum).toString() + " đ"
        binding.sumMoney.setText(price)
        var sumCommission = sum*(SplashActivity.PERCENT.toFloat()/100)
        val priceCommission = SplashActivity.formatterPrice.format(sumCommission).toString() + " đ"
        binding.tvSumCommission.setText(priceCommission)
    }

}