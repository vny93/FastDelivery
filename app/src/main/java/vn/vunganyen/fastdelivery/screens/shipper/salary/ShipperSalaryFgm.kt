package vn.vunganyen.fastdelivery.screens.shipper.salary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterShipperSalaryMng
import vn.vunganyen.fastdelivery.data.model.classSupport.MonthYearPickerDialog
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryReq
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryRes
import vn.vunganyen.fastdelivery.databinding.FragmentShipperSalaryFgmBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class ShipperSalaryFgm : Fragment(), ShipperSalaryItf {
    lateinit var binding : FragmentShipperSalaryFgmBinding
    lateinit var shipperSalaryPst: ShipperSalaryPst
    var adapter : AdapterShipperSalaryMng = AdapterShipperSalaryMng()
    lateinit var idshipper : String
    var c = Calendar.getInstance()
    var day = c.get(Calendar.DAY_OF_MONTH)
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var calFilter : Calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    var calStartMonth = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    var calEndMonth = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))

    var dateFrom = ""
    var dateTo = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShipperSalaryFgmBinding.inflate(layoutInflater)
        shipperSalaryPst = ShipperSalaryPst(this)
        idshipper = SplashActivity.profile.result.manv
        getData()
        setEvent()
        return binding.root
    }

    fun getData(){
        var req = ShipperSalaryReq(idshipper,dateFrom,dateTo)
        shipperSalaryPst.shipper_get_salary(req)
    }

    fun formatNameMonth(strMonth : String):String{
        var month : Date = SplashActivity.formatMonth3.parse(strMonth)
        var finalMonth = SplashActivity.formatMonth2.format(month)
        return finalMonth
    }

    fun setEvent(){
    //    binding.selectDate.text = ""+month.toString()+" "+year
        binding.selectDate.setOnClickListener {
            println("click")
            val newFragment: DialogFragment  = MonthYearPickerDialog(calFilter).apply {
                setListener { view, mYear, mMonth, dayOfMonth ->
                    //    Toast.makeText(requireContext(), "Set date: $mYear/$mMonth", Toast.LENGTH_LONG).show()
                    month = mMonth
                    year = mYear
                    calFilter.clear()
                    calFilter.set(year, month,1)
                    println("calFilter: "+ SplashActivity.formatdate.format(calFilter.time))
                    binding.selectDate.text = ""+(month+1).toString()+" - "+year
                    setDate()
                }
            }
            newFragment.show(this.requireFragmentManager(), "MonthYearPickerDialog")
           // show(parentFragmentManager, "MonthYearPickerDialog")
        }

        binding.refresh.setOnClickListener{
            dateFrom =""
            dateTo =""
            binding.selectDate.text = getString(R.string.title_time)
            var req = ShipperSalaryReq(idshipper,dateFrom,dateTo)
            shipperSalaryPst.shipper_get_salary(req)
        }
    }

    fun setDate(){
        day = 1
        println("day: "+day)
        println("month: "+month)
        println("year: "+year)
        //
        calStartMonth.clear()
        calStartMonth.set(year, month,day)
        calStartMonth.add(Calendar.MONTH,1)
        println("start month: "+ SplashActivity.formatdate.format(calStartMonth.time))
        //
        calEndMonth.clear()
        calEndMonth.set(year, month,day)
        calEndMonth.add(Calendar.MONTH,1)

        // ngày cuối của tháng hiện tại
        shipperSalaryPst.findEndOfMonth(calEndMonth)
        println("end month: "+ SplashActivity.formatdate.format(calEndMonth.time))

        var dateFrom = SplashActivity.formatdate.format(calStartMonth.time)
        var dateTo = SplashActivity.formatdate.format(calEndMonth.time)
        var req = ShipperSalaryReq(idshipper,dateFrom,dateTo)
        shipperSalaryPst.shipper_get_salary(req)
    }

    override fun shipper_get_salary(list: List<ShipperSalaryRes>) {
        adapter.setData(list)
        binding.rcvListSalary.adapter = adapter
        binding.rcvListSalary.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}