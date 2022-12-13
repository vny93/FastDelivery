package vn.vunganyen.fastdelivery.screens.admin.shopMng.update

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.auth.UpdateStatusAuthReq
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.shop.UpdateShopReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.databinding.ActivityUpdateShopMngBinding
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity
import vn.vunganyen.fastdelivery.screens.admin.warehouseMng.update.UpdateWarehouseActivity

class UpdateShopMngActivity : AppCompatActivity(), UpdateShopMngItf {
    lateinit var binding : ActivityUpdateShopMngBinding
    lateinit var updateShopMngPst: UpdateShopMngPst
    var dialog: StartAlertDialog = StartAlertDialog()
    var wards = ""
    var district=""
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()
    lateinit var reqShop : UpdateShopReq
    companion object{
        lateinit var shop : GetShopDetailRes
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateShopMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateShopMngPst = UpdateShopMngPst(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        shop = getIntent().getSerializableExtra("data") as GetShopDetailRes
        binding.edtShopName.setText(shop.tench)
        binding.edtPhoneShop.setText(shop.sdt)
        binding.edtEmailShop.setText(shop.email)
        var str = shop.diachi.trim()
        val arrWord = str.split(", ")
        binding.edtShopWay.setText(arrWord[0])
        binding.spinnerWards.setText(arrWord[1])
        wards = arrWord[1]
        binding.spinnerDistrict.setText(arrWord[2])
        district = arrWord[2]

        if(shop.trangthai == 0){
            binding.btnLock.setText(getString(R.string.tv_lock))
        }
        else{
            binding.btnLock.setText(getString(R.string.tv_activi))
        }
    }

    fun setEvent(){
        binding.spinnerDistrict.setOnItemClickListener(({ adapterView, view, i, l ->
            district = adapterView.getItemAtPosition(i).toString()
            for (list in listDistrict) {
                if (list.name.equals(adapterView.getItemAtPosition(i).toString())) {
                    println("code:" + list.code)
                    updateShopMngPst.getWards(list.code)
                }
            }
            println("district: " + district)
        }))

        binding.spinnerWards.setOnItemClickListener(({ adapterView, view, i, l ->
            wards = adapterView.getItemAtPosition(i).toString()
            println("wards: " + wards)
        }))

        binding.btnLock.setOnClickListener{
            if(binding.btnLock.text.toString().equals("Khóa tài khoản")){
                var req = UpdateStatusAuthReq(shop.tendangnhap,1)
                updateShopMngPst.updateStatus(req)
            }
            else{
                var req = UpdateStatusAuthReq(shop.tendangnhap,0)
                updateShopMngPst.updateStatus(req)
            }
        }

        binding.btnSave.setOnClickListener {
            if (binding.btnSave.text.toString().equals("Cập nhật")) {
                binding.btnLock.visibility = View.GONE
                binding.btnSave.setText("Lưu")

                updateShopMngPst.getDistrict()

                binding.cartName.setCardBackgroundColor(Color.WHITE)
                binding.edtShopName.setBackground(resources.getDrawable(R.color.white))
                binding.edtShopName.isEnabled = true

                binding.cartPhone.setCardBackgroundColor(Color.WHITE)
                binding.edtPhoneShop.setBackground(resources.getDrawable(R.color.white))
                binding.edtPhoneShop.isEnabled = true

                binding.cartEmail.setCardBackgroundColor(Color.WHITE)
                binding.edtEmailShop.setBackground(resources.getDrawable(R.color.white))
                binding.edtEmailShop.isEnabled = true

                binding.cartWay.setCardBackgroundColor(Color.WHITE)
                binding.edtShopWay.setBackground(resources.getDrawable(R.color.white))
                binding.edtShopWay.isEnabled = true

                binding.cartSpinnerDistric.setCardBackgroundColor(Color.WHITE)
                binding.spinnerDistrict.setBackground(resources.getDrawable(R.color.white))

                binding.cartSpinnerWards.setCardBackgroundColor(Color.WHITE)
                binding.spinnerWards.setBackground(resources.getDrawable(R.color.white))
            } else {
                var name = binding.edtShopName.text.toString()
                var way = binding.edtShopWay.text.toString()
                var phone = binding.edtPhoneShop.text.toString()
                var email = binding.edtEmailShop.text.toString()
                if(wards.equals("") || district.equals("") || way.equals("")){
                    dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.error_empty),this)
                }
                else{
                    var adress = binding.edtShopWay.text.toString() + ", "+ wards + ", " + district+", Thành phố Hồ Chí Minh"
                    println("adress: "+adress)
                    var req = UpdateShopReq(name,phone,email,adress,shop.mach)
                    updateShopMngPst.constraintCheck(req)
                }
            }
        }

        binding.lnlCustomStaff.setOnClickListener{
            binding.edtShopName.clearFocus()
            binding.edtShopWay.clearFocus()
            binding.edtPhoneShop.clearFocus()
            binding.edtEmailShop.clearFocus()
            binding.lnlCustomStaff.hideKeyboard()
        }
    }

    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }

    fun setToolbar() {
        var toolbar = binding.toolbarUpdateShop
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getListDistrict(list: List<DistrictRes>) {
        listDistrict = list as ArrayList<DistrictRes>
        for (i in 0..list.size - 1) {
            listDistrictName.add(list.get(i).name)
        }
        setAdapterDistrict(listDistrictName)
    }

    fun setAdapterDistrict(list: List<String>) {
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerDistrict.setAdapter(adapter)
    }

    override fun getListWards(list: List<WardsRes>) {
        for (i in 0..list.size - 1) {
            listWardstName.add(list.get(i).name)
        }
        setAdapterWards(listWardstName)
    }

    fun setAdapterWards(list: List<String>) {
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerWards.setAdapter(adapter)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.error_empty),this)
    }

    override fun PhoneIllegal() {
        dialog.showStartDialog3(getString(R.string.PhoneIllegal),this)
    }

    override fun EmailIllegal() {
        dialog.showStartDialog3(getString(R.string.EmailIllegal),this)
    }

    override fun PhoneExist() {
        dialog.showStartDialog3(getString(R.string.PhoneExist),this)
    }

    override fun EmailExist() {
        dialog.showStartDialog3(getString(R.string.EmailExist),this)
    }

    override fun UpdateSucces() {
        dialog.showStartDialog3(getString(R.string.UpdateSucces),this)
        binding.btnSave.setText(getString(R.string.tv_save))
        binding.btnLock.visibility = View.VISIBLE

        binding.cartName.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtShopName.setBackground(resources.getDrawable(R.color.gray))
        binding.edtShopName.isEnabled = false

        binding.cartWay.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtShopWay.setBackground(resources.getDrawable(R.color.gray))
        binding.edtShopWay.isEnabled = false

        binding.cartPhone.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtPhoneShop.setBackground(resources.getDrawable(R.color.gray))
        binding.edtPhoneShop.isEnabled = false

        binding.cartEmail.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtEmailShop.setBackground(resources.getDrawable(R.color.gray))
        binding.edtEmailShop.isEnabled = false

        binding.cartSpinnerDistric.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerDistrict.setBackground(resources.getDrawable(R.color.gray))

        binding.cartSpinnerWards.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerWards.setBackground(resources.getDrawable(R.color.gray))

        val mlist = mutableListOf("")
        mlist.clear()
        setAdapterDistrict(mlist)
        setAdapterWards(mlist)
    }

    override fun lockAccount() {
        dialog.showStartDialog3(getString(R.string.tv_lock_success),this)
        binding.btnLock.setText(getString(R.string.tv_activi))
    }

    override fun activeAccount() {
        dialog.showStartDialog3(getString(R.string.tv_activi_success),this)
        binding.btnLock.setText(getString(R.string.tv_lock))
    }
}