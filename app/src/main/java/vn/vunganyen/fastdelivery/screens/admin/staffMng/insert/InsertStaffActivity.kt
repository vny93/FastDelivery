package vn.vunganyen.fastdelivery.screens.admin.staffMng.insert

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.auth.AuthRegisterReq
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.role.MainListRoleRes
import vn.vunganyen.fastdelivery.data.model.staff.AutomaticIdReq
import vn.vunganyen.fastdelivery.data.model.staff.InsertShipperReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.databinding.ActivityInsertStaffBinding
import vn.vunganyen.fastdelivery.databinding.ActivityStaffMngBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class InsertStaffActivity : AppCompatActivity(),InsertStaffItf {
    lateinit var binding : ActivityInsertStaffBinding
    lateinit var insertStaffPts: InsertStaffPts
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    var listRole = ArrayList<ListRoleRes>()
    var listRoleName = ArrayList<String>()
    var listWarehouse = ArrayList<WarehouseRes>()
    var listWarehouseName = ArrayList<String>()
    var WarehouseId = 0
    lateinit var reqStaff : ProfileRes
    lateinit var reqShipper : InsertShipperReq
    lateinit var reqAuth : AuthRegisterReq
    companion object{
        var roleId = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertStaffPts = InsertStaffPts(this)
        getData()
        setEvent()
        setToolbar()
    }

    fun getData(){
        insertStaffPts.getListRole()
    }

    fun setEvent(){
        binding.edtBirthStaff.setText("" + day + "/" + (month + 1) + "/" + year)
        binding.imvCalendar.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.edtBirthStaff.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
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

        binding.spinnerRole.setOnItemClickListener(({adapterView,view,i,l ->
            for(list in listRole){
                if(list.tenquyen.equals(adapterView.getItemAtPosition(i).toString())){
                    roleId = list.maquyen
                    println(list.maquyen)
                    if(roleId == SplashActivity.SHIPPER){
                        binding.tvWarehouse.visibility = View.GONE
                        binding.cartSpinnerWarehouse.visibility = View.GONE
                    }
                    else{
                        binding.tvWarehouse.visibility = View.VISIBLE
                        binding.cartSpinnerWarehouse.visibility = View.VISIBLE
                    }
                    insertStaffPts.automaticId(AutomaticIdReq(roleId))
                }
            }
        }))

        binding.spinnerWarehouse.setOnItemClickListener(({adapterView,view,i,l ->
            for(list in listWarehouse){
                if(list.tenkho.equals(adapterView.getItemAtPosition(i).toString())){
                    WarehouseId = list.makho
                    println(list.makho)
                }
            }
        }))

        binding.btnSave.setOnClickListener{
            val id = binding.edtStaffId.text.toString()
            var cmnd = binding.edtStaffCmnd.text.toString()
            val name = binding.edtNameStaff.text.toString()
            val dateBirth = binding.edtBirthStaff.text.toString()
            val mdate : Date = SplashActivity.formatdate1.parse(dateBirth)
            var strDate = SplashActivity.formatdate.format(mdate)
            var email = binding.edtEmailStaff.text.toString()
            var phone = binding.edtPhoneStaff.text.toString()
            var address = binding.edtAddressStaff.text.toString()
            var gender = ""
            if(binding.radioMaleStaff.isChecked == true){
                gender = "Nam"
            }
            if(binding.radioFemaleStaff.isChecked == true){
                gender = "Nữ"
            }
            println("cmnd: "+cmnd)
            println("name: "+name)
            println("strDate: "+strDate)
            println("gendar: "+gender)
            println("email: "+email)
            println("phone: "+phone)
            println("address: "+address)
            println("quyền: "+ roleId)
            println("kho: "+ WarehouseId)
            var username = binding.edtUsernameStaff.text.toString()
            var pass = binding.edtPasswordStaff.text.toString()
            reqAuth = AuthRegisterReq(username,pass,roleId)
            reqStaff = ProfileRes(id.toUpperCase(),cmnd,name,gender,strDate,phone,email,address,WarehouseId,username)
            reqShipper = InsertShipperReq(id.toUpperCase(),cmnd,name,gender,strDate,phone,email,address,username)
            insertStaffPts.constraintCheck(reqAuth,reqStaff)

        }
        binding.lnlCustomStaff.setOnClickListener{
            binding.edtUsernameStaff.clearFocus()
            binding.edtPasswordStaff.clearFocus()
            binding.edtStaffCmnd.clearFocus()
            binding.edtNameStaff.clearFocus()
            binding.edtBirthStaff.clearFocus()
            binding.edtPhoneStaff.clearFocus()
            binding.edtEmailStaff.clearFocus()
            binding.edtAddressStaff.clearFocus()
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
        var toolbar = binding.toolbarInsertStaff
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getRoleSuccess(list: List<ListRoleRes>) {
        listRole = list as ArrayList<ListRoleRes>
        for(i in 0..list.size-1){
            listRoleName.add(list.get(i).tenquyen)
        }
        setAdapterRole(listRoleName)
        insertStaffPts.getListWarehouse()
    }

    override fun getWarehouseSuccess(list: List<WarehouseRes>) {
        listWarehouse = list as ArrayList<WarehouseRes>
        for(i in 0..list.size-1){
            listWarehouseName.add(list.get(i).tenkho)
        }
        setAdapterWh(listWarehouseName)
    }

    fun setAdapterRole(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerRole.setAdapter(adapter)
        binding.spinnerRole.setHint("Quyền")
    }

    fun setAdapterWh(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerWarehouse.setAdapter(adapter)
        binding.spinnerWarehouse.setHint("Kho")
    }


    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun CmndIllegal() {
        dialog.showStartDialog3(getString(R.string.CmndIllegal),this)
    }

    override fun PhoneIllegal() {
        dialog.showStartDialog3(getString(R.string.PhoneIllegal),this)
    }

    override fun EmailIllegal() {
        dialog.showStartDialog3(getString(R.string.EmailIllegal),this)
    }

    override fun CmndExist() {
        dialog.showStartDialog3(getString(R.string.CmndExist),this)
    }

    override fun PhoneExist() {
        dialog.showStartDialog3(getString(R.string.PhoneExist),this)
    }

    override fun EmailExist() {
        dialog.showStartDialog3(getString(R.string.EmailExist),this)
    }

    override fun AddSucces() {
        dialog.showStartDialog3(getString(R.string.tv_rgt_staffSuccess),this)
        binding.edtUsernameStaff.setText("")
        binding.edtPasswordStaff.setText("")
        binding.edtStaffCmnd.setText("")
        binding.edtStaffId.setText("")
        binding.edtNameStaff.setText("")
        binding.edtPhoneStaff.setText("")
        binding.edtEmailStaff.setText("")
        binding.edtAddressStaff.setText("")
        binding.spinnerRole.setText("",false)
        binding.spinnerWarehouse.setText("",false)
        binding.radioMaleStaff.setChecked(false)
        binding.radioFemaleStaff.setChecked(false)
    }

    override fun AccountExist() {
        dialog.showStartDialog3(getString(R.string.AccountExist),this)
    }

    override fun UserIllegal() {
        dialog.showStartDialog3(getString(R.string.UserIllegal),this)
    }

    override fun PassIllegal() {
        dialog.showStartDialog3(getString(R.string.PassIllegal),this)
    }

    override fun CheckSuccess() {
        insertStaffPts.insertAccount(reqAuth,reqStaff,reqShipper)
    }

    override fun OrlError() {
        dialog.showStartDialog3(getString(R.string.staffId_old),this)
    }

    override fun AutomaticId(id: String) {
        binding.edtStaffId.setText(id)
    }
}