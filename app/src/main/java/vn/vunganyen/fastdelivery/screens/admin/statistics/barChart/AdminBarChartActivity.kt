package vn.vunganyen.fastdelivery.screens.admin.statistics.barChart

import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import vn.vunganyen.fastdelivery.data.model.classSupport.YearPickerDialog
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverReq
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes
import vn.vunganyen.fastdelivery.databinding.ActivityAdminBarChartBinding
import vn.vunganyen.fastdelivery.screens.admin.statistics.turnover.TurnoverActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*
import kotlin.collections.ArrayList


class AdminBarChartActivity : AppCompatActivity(), AdminBarChartItf {
    lateinit var binding : ActivityAdminBarChartBinding
    lateinit var adminBarCharPst: AdminBarCharPst
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var calFilter : Calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    companion object{
        lateinit var listNew : java.util.ArrayList<TurnoverRes>
        var dateStr = ""
        var dateEnd = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBarChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adminBarCharPst = AdminBarCharPst(this)
     //   setDataChart()
        setToolbar()
        setData(year)
        setEvent()
    }

    fun setEvent() {
        binding.selectDate.text = year.toString()
        binding.selectDate.setOnClickListener {
            YearPickerDialog(calFilter).apply {
                setListener { view, mYear, mMonth, dayOfMonth ->
                    //    Toast.makeText(requireContext(), "Set date: $mYear/$mMonth", Toast.LENGTH_LONG).show()
                    year = mYear
                    calFilter.clear()
                    calFilter.set(year, 1,1)
                    binding.selectDate.text = year.toString()
                    setData(year)
                }
                show(supportFragmentManager, "MonthYearPickerDialog")
            }
        }
    }

    fun setData(year : Int){
        var dateFrom = year.toString()+"-01-01"
        var dateTo = year.toString()+"-12-31 23:59:59"
        dateStr = "01-"+year
        dateEnd = "12-"+year
        listNew = ArrayList<TurnoverRes>()
        adminBarCharPst.getTurnover(TurnoverReq(dateFrom,dateTo))
    }

    fun setDataChart(list: List<TurnoverRes>){
        var visitors = ArrayList<BarEntry>()
        for(x in list){
            visitors.add(BarEntry(x.thang.toFloat(),x.doanhthu))
        }

        var barDataSet = BarDataSet(visitors,"Visitors")
        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(this, R.color.holo_orange_light))
        colors.add(ContextCompat.getColor(this, R.color.holo_orange_dark))
        colors.add(ContextCompat.getColor(this, R.color.holo_red_light))
        colors.add(ContextCompat.getColor(this, R.color.holo_red_dark))
        colors.add(ContextCompat.getColor(this, R.color.holo_green_light))
        colors.add(ContextCompat.getColor(this, R.color.holo_green_dark))
        colors.add(ContextCompat.getColor(this, R.color.holo_blue_bright))
        colors.add(ContextCompat.getColor(this, R.color.holo_blue_light))
        colors.add(ContextCompat.getColor(this, R.color.holo_blue_dark))
        colors.add(ContextCompat.getColor(this, R.color.holo_purple))

        barDataSet.setColors(colors)

        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 13f
        var barData = BarData(barDataSet)
        binding.barChart.setFitBars(true)
        binding.barChart.data = barData
        binding.barChart.description.text = "Biểu đồ cột"
     //   binding.barChart.animateY(2000)
    }

    override fun getTurnover(list: List<TurnoverRes>) {
        println("call chart")
        setDataChart(list)
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
}