package vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.databinding.ActivityParcelDetailBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ParcelDetailActivity : AppCompatActivity(),ParcelDetailItf {
    lateinit var binding : ActivityParcelDetailBinding
    lateinit var parelDetailPts: ParelDetailPts
    lateinit var data : AdGetParcelRes
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