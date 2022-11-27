package vn.vunganyen.fastdelivery.screens.staff.salary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterShipperSalaryMng
import vn.vunganyen.fastdelivery.data.model.classSupport.MonthYearPickerDialog
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryReq
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryRes
import vn.vunganyen.fastdelivery.databinding.FragmentStaffSalaryFgmBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class StaffSalaryFgm : Fragment(), StaffSalaryItf {
    lateinit var binding : FragmentStaffSalaryFgmBinding
    lateinit var staffSalaryPst: StaffSalaryPst
    var adapter : AdapterShipperSalaryMng = AdapterShipperSalaryMng()
    lateinit var idstaff : String
    var day = GregorianCalendar(TimeZone.getTimeZone("GMT+7")).get(Calendar.DAY_OF_MONTH)
    var year = GregorianCalendar(TimeZone.getTimeZone("GMT+7")).get(Calendar.YEAR)
    var month = GregorianCalendar(TimeZone.getTimeZone("GMT+7")).get(Calendar.MONTH)
    var calFilter : Calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    var calStartMonth = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    var calEndMonth = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))

    var dateFrom = ""
    var dateTo = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStaffSalaryFgmBinding.inflate(layoutInflater)
        staffSalaryPst = StaffSalaryPst(this)
        idstaff = SplashActivity.profile.result.manv
        getData()
        setEvent()
        return binding.root
    }

    fun getData(){
        var req = ShipperSalaryReq(idstaff,dateFrom,dateTo)
        staffSalaryPst.staff_get_salary(req)
    }

    fun setEvent(){
        //    binding.selectDate.text = ""+month.toString()+" "+year
        binding.selectDate.setOnClickListener {
            println("click")
            val newFragment: DialogFragment = MonthYearPickerDialog(calFilter).apply {
                setListener { view, mYear, mMonth, dayOfMonth ->
                    //    Toast.makeText(requireContext(), "Set date: $mYear/$mMonth", Toast.LENGTH_LONG).show()
                    month = mMonth
                    year = mYear
                    calFilter.clear()
                    calFilter.set(year, month,1)
                    binding.selectDate.text = ""+month.toString()+" - "+year
                    setDate()
                }
            }
            newFragment.show(this.requireFragmentManager(), "MonthYearPickerDialog")
            // show(parentFragmentManager, "MonthYearPickerDialog")
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
        println("start month: "+ SplashActivity.formatdate.format(calStartMonth.time))
        //
        calEndMonth.clear()
        calEndMonth.set(year, month,day)

        // ngày cuối của tháng hiện tại
        staffSalaryPst.findEndOfMonth(calEndMonth)
        println("end month: "+ SplashActivity.formatdate.format(calEndMonth.time))

        var dateFrom = SplashActivity.formatdate.format(calStartMonth.time)
        var dateTo = SplashActivity.formatdate.format(calEndMonth.time)
        var req = ShipperSalaryReq(idstaff,dateFrom,dateTo)
        staffSalaryPst.staff_get_salary(req)
    }

    override fun staff_get_salary(list: List<ShipperSalaryRes>) {
        adapter.setData(list)
        binding.rcvListSalary.adapter = adapter
        binding.rcvListSalary.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}