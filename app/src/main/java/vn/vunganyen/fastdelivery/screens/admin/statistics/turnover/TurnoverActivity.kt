package vn.vunganyen.fastdelivery.screens.admin.statistics.turnover

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterTurnover
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverReq
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes
import vn.vunganyen.fastdelivery.databinding.ActivityTurnoverBinding
import vn.vunganyen.fastdelivery.screens.admin.statistics.barChart.AdminBarChartActivity
import vn.vunganyen.fastdelivery.screens.admin.statistics.statistics.StatisticsActivity
import vn.vunganyen.fastdelivery.screens.admin.statistics.statistics.StatisticsPst
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class TurnoverActivity : AppCompatActivity(), TurnoverItf {
    lateinit var binding : ActivityTurnoverBinding
    lateinit var turnoverPst: TurnoverPst
    var adapter : AdapterTurnover = AdapterTurnover()
    var c = Calendar.getInstance()
    var day = c.get(Calendar.DAY_OF_MONTH)
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    var dateFrom = ""
    var dateTo = ""
    companion object{
        var sum = 0.0F
        lateinit var listNew : ArrayList<TurnoverRes>
        var dateStr = ""
        var dateEnd = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTurnoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        turnoverPst = TurnoverPst(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        binding.lnlSeen.visibility = View.GONE
    }

    fun setEvent() {
        binding.imvDateFrom.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.selectFrom.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                            day = mDay
                            month = mMonth
                            year = mYear
                        },
                        year,
                        month,
                        day
                    )
                }
            if (dpd != null) {
                dpd.show()
            }
        }

        binding.imvDateTo.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.selectTo.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                            day = mDay
                            month = mMonth
                            year = mYear
                        },
                        year,
                        month,
                        day
                    )
                }
            if (dpd != null) {
                dpd.show()
            }
        }

        binding.btnFilter.setOnClickListener {
            var strFrom = binding.selectFrom.text.toString()
            var strTo = binding.selectTo.text.toString()
            if (!strFrom.equals("")) {
                val mdateFrom: Date =
                    SplashActivity.formatdate1.parse(binding.selectFrom.text.toString())
                dateFrom = SplashActivity.formatdate.format(mdateFrom)+" 00:00:00"
                println("dateFrom: " + dateFrom)
                dateStr = SplashActivity.formatMonthYear.format(mdateFrom)
            }
            if (!strTo.equals("")) {
                val mdateTo: Date =
                    SplashActivity.formatdate1.parse(binding.selectTo.text.toString())
                dateTo = SplashActivity.formatdate.format(mdateTo)+" 23:59:59"
                println("dateTo: " + dateTo)
                dateEnd = SplashActivity.formatMonthYear.format(mdateTo)
            }
            sum = 0.0F
            listNew = ArrayList<TurnoverRes>()
            turnoverPst.validCheck(TurnoverReq(dateFrom,dateTo))
        }

        binding.lnlSeen.setOnClickListener{
            var intent = Intent(this, StatisticsActivity::class.java)
            intent.putExtra("dateFrom",dateFrom)
            intent.putExtra("dateTo",dateTo)
            startActivity(intent)
        }

        binding.lnlStatisticsYear.setOnClickListener{
            var intent = Intent(this, AdminBarChartActivity::class.java)
            startActivity(intent)
        }
    }

    fun setToolbar() {
        var toolbar = binding.toolbarTop
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getTurnover(list: List<TurnoverRes>) {
        adapter.setData(list)
        binding.rcvListStt.adapter = adapter
        binding.rcvListStt.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val sum_money = SplashActivity.formatter.format(sum).toString() + " đ"
        println(sum)
        binding.sumMoney.setText(sum_money)
        binding.lnlSeen.visibility = View.VISIBLE
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty2),this)
    }

    override fun DateError() {
        dialog.showStartDialog3(getString(R.string.errorDate3),this)
    }
}