package vn.vunganyen.fastdelivery.screens.shop.registerShop

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.shop.AddShopReq
import vn.vunganyen.fastdelivery.databinding.ActivityShopRegisterBinding
import vn.vunganyen.fastdelivery.databinding.DialogChooseAdressBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class ShopRegisterActivity : AppCompatActivity(),ShopRegisterItf {
    lateinit var binding : ActivityShopRegisterBinding
    lateinit var shopRegisterPst: ShopRegisterPst
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var reqAuth : AuthRegisterReq
    lateinit var reqShop : AddShopReq
    lateinit var dialog2: Dialog
    lateinit var bindingDialogAdress : DialogChooseAdressBinding
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()
    var district = ""
    var wards = ""

    var idShop = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shopRegisterPst = ShopRegisterPst(this)
        dialog2 = Dialog(this@ShopRegisterActivity)
        bindingDialogAdress = DialogChooseAdressBinding.inflate(layoutInflater)
        dialog2.setContentView(bindingDialogAdress.root)
        setToolbar()
        getData()
        setEvent()
    }

    fun getData(){
        shopRegisterPst.autoId()
        binding.edtAddressShop.setFocusable(false)
        shopRegisterPst.getDistrict()
    }

    fun setEvent(){

        binding.edtAddressShop.setOnClickListener{
            println("Chọn địa chỉ")
            showDialogSetting(Gravity.CENTER)
        }

        bindingDialogAdress.spinnerDistrict.setOnItemClickListener(({ adapterView, view, i, l ->
            district = adapterView.getItemAtPosition(i).toString()
            for (list in listDistrict) {
                if (list.name.equals(adapterView.getItemAtPosition(i).toString())) {
                    println("code:" + list.code)
                    shopRegisterPst.getWards(list.code)
                }
            }
            println("district: " + district)
        }))

        bindingDialogAdress.spinnerWards.setOnItemClickListener(({ adapterView, view, i, l ->
            wards = adapterView.getItemAtPosition(i).toString()
            println("wards: " + wards)
        }))

        binding.btnRegister.setOnClickListener{
            var username = binding.edtUsername.text.toString()
            var password = binding.edtPassword.text.toString()
            var name = binding.edtNameShopf.text.toString()
            var phone = binding.edtPhoneShop.text.toString()
            var email = binding.edtEmailShop.text.toString()
            var adress = binding.edtAddressShop.text.toString()
            reqAuth = AuthRegisterReq(username,password,SplashActivity.STORE)
            reqShop = AddShopReq(idShop,name,phone,email,adress,username)
            shopRegisterPst.constrainCheck(reqAuth,reqShop)
        }

        binding.lnl1.setOnClickListener{
            binding.edtUsername.clearFocus()
            binding.edtPassword.clearFocus()
            binding.edtNameShopf.clearFocus()
            binding.edtPhoneShop.clearFocus()
            binding.edtEmailShop.clearFocus()
            binding.edtAddressShop.clearFocus()
            binding.lnl1.hideKeyboard()
        }
    }

    fun showDialogSetting(gravity : Int){
        val window = dialog2.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.CENTER == gravity) {
            dialog2.setCancelable(false)
        } else {
            dialog2.setCancelable(false)
        }

        bindingDialogAdress.btnAccept.setOnClickListener{
            var way = bindingDialogAdress.edtWay.text.toString()
            if(wards.equals("") || district.equals("") || way.equals("")){
                dialog.showStartDialog3(getString(R.string.error_empty),this)
            }
            else{
                var adress = way + ", "+ wards + ", " + district +", Thành phố Hồ Chí Minh"
                println("adress: "+adress)
                binding.edtAddressShop.setText(adress)
                dialog2.dismiss()
            }
        }

        bindingDialogAdress.btnCancel.setOnClickListener{
            dialog2.dismiss()
        }
        bindingDialogAdress.dialogSend.setOnClickListener{
            bindingDialogAdress.edtWay.clearFocus()
            bindingDialogAdress.dialogSend.hideKeyboard()
        }

        dialog2.show()
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
        var toolbar = binding.toolbarInsertStaff
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
        bindingDialogAdress.spinnerDistrict.setAdapter(adapter)
    }

    override fun getListWards(list: List<WardsRes>) {
        for (i in 0..list.size - 1) {
            listWardstName.add(list.get(i).name)
        }
        setAdapterWards(listWardstName)
    }

    fun setAdapterWards(list: List<String>) {
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        bindingDialogAdress.spinnerWards.setAdapter(adapter)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun UserIllegal() {
        dialog.showStartDialog3(getString(R.string.UserIllegal),this)
    }

    override fun PassIllegal() {
        dialog.showStartDialog3(getString(R.string.PassIllegal),this)
    }

    override fun PhoneIllegal() {
        dialog.showStartDialog3(getString(R.string.PhoneIllegal),this)
    }

    override fun EmailIllegal() {
        dialog.showStartDialog3(getString(R.string.EmailIllegal),this)
    }

    override fun AccountExist() {
        dialog.showStartDialog3(getString(R.string.AccountExist),this)
    }

    override fun PhoneExist() {
        dialog.showStartDialog3(getString(R.string.PhoneExist),this)
    }

    override fun EmailExist() {
        dialog.showStartDialog3(getString(R.string.EmailExist),this)
    }

    override fun CheckSuccess() {
        shopRegisterPst.insertAccount(reqAuth,reqShop)
    }

    override fun AutomaticId(id: String) {
        idShop = id
    }

    override fun AddSucces() {
        dialog.showStartDialog3(getString(R.string.tv_rgt_shopSuccess),this)
        binding.edtUsername.setText("")
        binding.edtPassword.setText("")
        binding.edtNameShopf.setText("")
        binding.edtPhoneShop.setText("")
        binding.edtEmailShop.setText("")
        binding.edtAddressShop.setText("")
    }
}