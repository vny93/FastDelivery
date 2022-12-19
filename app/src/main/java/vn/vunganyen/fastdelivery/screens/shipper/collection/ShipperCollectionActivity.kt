package vn.vunganyen.fastdelivery.screens.shipper.collection

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterAdminCollection
import vn.vunganyen.fastdelivery.data.adapter.AdapterShipperCollection
import vn.vunganyen.fastdelivery.data.adapter.AdapterTurnover
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.salary.CollectionRes
import vn.vunganyen.fastdelivery.data.model.salary.ShipperCollectionReq
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverReq
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes
import vn.vunganyen.fastdelivery.databinding.ActivityShipperCollectionBinding
import vn.vunganyen.fastdelivery.screens.admin.statistics.barChart.AdminBarChartActivity
import vn.vunganyen.fastdelivery.screens.admin.statistics.statistics.StatisticsActivity
import vn.vunganyen.fastdelivery.screens.admin.statistics.turnover.TurnoverActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class ShipperCollectionActivity : AppCompatActivity(), ShipperCollectionItf {
    lateinit var binding : ActivityShipperCollectionBinding
    lateinit var shipperCollectionPst : ShipperCollectionPst
    var adapter : AdapterShipperCollection = AdapterShipperCollection()
    var c = Calendar.getInstance()
    var day = c.get(Calendar.DAY_OF_MONTH)
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    var dateFrom = ""
    var dateTo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipperCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shipperCollectionPst = ShipperCollectionPst(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
       // binding.lnlSeen.visibility = View.GONE
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
                TurnoverActivity.dateStr = SplashActivity.formatMonthYear.format(mdateFrom)
            }
            if (!strTo.equals("")) {
                val mdateTo: Date =
                    SplashActivity.formatdate1.parse(binding.selectTo.text.toString())
                dateTo = SplashActivity.formatdate.format(mdateTo)+" 23:59:59"
                println("dateTo: " + dateTo)
                TurnoverActivity.dateEnd = SplashActivity.formatMonthYear.format(mdateTo)
            }
            shipperCollectionPst.validCheck(ShipperCollectionReq(SplashActivity.profile.result.manv,dateFrom,dateTo))
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

    override fun getListCollection(list: List<CollectionRes>) {
        println("đây")
        setAdapter(list)
    }

    fun setAdapter(list : List<CollectionRes>){
        var sum_shop = 0.0f
        var sum_ship = 0.0f
        for(i in 0..list.size-1){
            sum_shop = sum_shop + list.get(i).sotien
            sum_ship = sum_ship + list.get(i).phigiao
        }
        adapter.setData(list)
        binding.rcvListCollection.adapter = adapter
        binding.rcvListCollection.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var sum = sum_shop + sum_ship
        println("sum: "+sum)
        val sumCollection = SplashActivity.formatterPrice.format(sum).toString() + " đ"
        binding.sumMoney.setText(sumCollection)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty2),this)
    }

    override fun DateError() {
        dialog.showStartDialog3(getString(R.string.errorDate3),this)
    }
}