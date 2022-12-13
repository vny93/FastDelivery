package vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.data.adapter.AdapterDetailParcel
import vn.vunganyen.fastdelivery.data.adapter.AdapterStatusDetail
import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelReq
import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.FullStatusDetailReq
import vn.vunganyen.fastdelivery.data.model.parcel.FullStatusDetailRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.databinding.ActivityParcelDetailBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ParcelDetailActivity : AppCompatActivity(),ParcelDetailItf {
    lateinit var binding : ActivityParcelDetailBinding
    lateinit var parelDetailPts: ParelDetailPts
    lateinit var data : AdGetParcelRes
    var adapterDetailParcel : AdapterDetailParcel = AdapterDetailParcel()
    var adapterStatusDetaillMng : AdapterStatusDetail = AdapterStatusDetail()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parelDetailPts = ParelDetailPts(this)
        getData()
        setToolbar()
    }

    fun getData(){
        data = getIntent().getSerializableExtra("data") as AdGetParcelRes
        parelDetailPts.getShopDetail(GetShopDetailReq(data.mach))
        parelDetailPts.getDateilParcel(GetDetailParcelReq(data.mabk))
        parelDetailPts.fullStatusDetail(FullStatusDetailReq(data.mabk))
    }

    override fun getShopDetail(res: GetShopDetailRes) {
        binding.tvadNameDtpc.setText(res.tench)
        binding.tvadPhoneDtpc.setText(res.sdt)
        binding.tvadAdressDtpc.setText(res.diachi)

        binding.tvadNameDtpc2.setText(data.hotennguoinhan)
        binding.tvadPhoneDtpc2.setText(data.sdtnguoinhan)
        binding.tvadAdressDtpc2.setText(data.diachinguoinhan)

        binding.tvadMassDtpc.setText(data.khoiluong.toString()+" kg")
        binding.tvadSizeDtpc.setText(data.kichthuoc)
        binding.tvadNoteDtpc.setText(data.ghichu)
        binding.tvadTransDtpc.setText(data.htvanchuyen)
        val price = SplashActivity.formatter.format(data.sotien).toString() + " đ"
        binding.tvadPriceDtpc.setText(price)
        val priceShip = SplashActivity.formatter.format(data.phigiao).toString() + " đ"
        binding.tvadPriceShipDtpc.setText(priceShip)
        binding.paymentMethodDtpc.setText(data.ptthanhtoan)
        binding.paymantStatusDtpc.setText(data.tinhtrangthanhtoan)
    }

    override fun getDetailParcel(list: List<GetDetailParcelRes>) {
        adapterDetailParcel.setData(list)
        binding.rcvListDetailParcel.adapter = adapterDetailParcel
        binding.rcvListDetailParcel.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun fullStatusDetail(list: List<FullStatusDetailRes>) {
        adapterStatusDetaillMng.setData(list)
        binding.rcvListDetailStatus.adapter = adapterStatusDetaillMng
        binding.rcvListDetailStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    fun setToolbar() {
        var toolbar = binding.toolbarParcelDetail
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}