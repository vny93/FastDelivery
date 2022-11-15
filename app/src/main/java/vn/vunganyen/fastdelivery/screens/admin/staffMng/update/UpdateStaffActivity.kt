package vn.vunganyen.fastdelivery.screens.admin.staffMng.update

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelReq
import vn.vunganyen.fastdelivery.data.model.staff.AdminUpdateReq
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhRes
import vn.vunganyen.fastdelivery.data.model.way.WayReq
import vn.vunganyen.fastdelivery.databinding.ActivityUpdateStaffBinding
import vn.vunganyen.fastdelivery.databinding.DialogChooseAdressBinding
import vn.vunganyen.fastdelivery.databinding.DialogSettingWarehouseBinding
import vn.vunganyen.fastdelivery.screens.admin.parcelMng.assignment.AssignmentMngActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class UpdateStaffActivity : AppCompatActivity(),UpdateStaffItf {
    lateinit var binding : ActivityUpdateStaffBinding
    lateinit var updateStaffPst: UpdateStaffPst
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var dialog2: Dialog
    lateinit var bindingDialog : DialogChooseAdressBinding
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()
    var adress = ""
    var wards = ""
    var strAdress = ""

    lateinit var reqStaff : AdminUpdateReq

    companion object{
        lateinit var staff : ListStaffRes
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateStaffPst = UpdateStaffPst(this)
        dialog2 = Dialog(this@UpdateStaffActivity)
        bindingDialog = DialogChooseAdressBinding.inflate(layoutInflater)
        dialog2.setContentView(bindingDialog.root)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        staff = getIntent().getSerializableExtra("data") as ListStaffRes

        binding.edtStaffId.setText(staff.manv)
        binding.edtStaffRole.setText(staff.tenquyen)
        binding.edtNameStaff.setText(staff.hoten)
        binding.edtStaffCmnd.setText(staff.cmnd)
        binding.edtEmailStaff.setText(staff.email)
        binding.edtPhoneStaff.setText(staff.sdt)
        binding.edtAddressStaff.setText(staff.diachi)
        var date : Date = SplashActivity.formatdate.parse(staff.ngaysinh)
        c.time = date
        c.add(Calendar.DATE, 1) // number of days to add
        var strDate = SplashActivity.formatdate1.format(c.time)
        binding.edtBirthStaff.setText(strDate)

        if(staff.gioitinh.equals("Nam")){
            binding.radioMaleStaff.isChecked = true
        }
        else binding.radioFemaleStaff.isChecked = true
        updateStaffPst.getDistrict()
    }

    fun setEvent(){
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

        binding.edtAddressStaff.setOnClickListener{
            println("Chọn địa chỉ")
            showDialogSetting(Gravity.CENTER)
        }

        binding.btnSave.setOnClickListener{
            if(binding.btnSave.text.toString().equals("Cập nhật")){
                binding.btnSave.setText("Lưu")

                binding.cartStaffCmnd.setCardBackgroundColor(Color.WHITE)
                binding.edtStaffCmnd.setBackground(resources.getDrawable(R.color.white))
                binding.edtStaffCmnd.isEnabled = true

                binding.cartNameStaff.setCardBackgroundColor(Color.WHITE)
                binding.edtNameStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtNameStaff.isEnabled = true

                binding.cartBirthStaff.setCardBackgroundColor(Color.WHITE)
                binding.edtBirthStaff.setBackground(resources.getDrawable(R.color.white))

                binding.radioMaleStaff.isEnabled = true
                binding.radioFemaleStaff.isEnabled = true

                binding.cartPhone.setCardBackgroundColor(Color.WHITE)
                binding.edtPhoneStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtPhoneStaff.isEnabled = true

                binding.cartEmailStaff.setCardBackgroundColor(Color.WHITE)
                binding.edtEmailStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtEmailStaff.isEnabled = true

                binding.cartAddress.setCardBackgroundColor(Color.WHITE)
                binding.edtAddressStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtAddressStaff.isEnabled = true
                binding.edtAddressStaff.setFocusable(false)
            }
            else{
                var id = binding.edtStaffId.text.toString()
                var cmnd = binding.edtStaffCmnd.text.toString()
                var name = binding.edtNameStaff.text.toString()
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
                println("name: "+name)
                println("cmnd: "+cmnd)
                println("strDate: "+strDate)
                println("gendar: "+gender)
                println("email: "+email)
                println("phone: "+phone)
                println("address: "+address)
                reqStaff = AdminUpdateReq(id,cmnd,name,gender,strDate,phone,email,address)
                updateStaffPst.validCheckUpdate(reqStaff)
            }
        }

        bindingDialog.spinnerDistrict.setOnItemClickListener(({adapterView, view, i , l ->
            adress = adapterView.getItemAtPosition(i).toString()
            if(adress.equals("Tất cả")){
                adress = ""
                val mlist = mutableListOf("")
                mlist.clear()
                setAdapterWards(mlist)
                bindingDialog.spinnerWards.setText("")
            }
            else{
                for(list in listDistrict){
                    if(list.name.equals(adapterView.getItemAtPosition(i).toString())){
                        println("code:"+list.code)
                        updateStaffPst.getWards(list.code)
                    }
                }
            }
            println("adress: "+adress)
            strAdress = adress
        }))

        bindingDialog.spinnerWards.setOnItemClickListener(({adapterView, view, i , l ->
            wards = adapterView.getItemAtPosition(i).toString()+", "+adress
            println("adress: "+wards)
            strAdress = wards
        }))

        binding.lnlCustomStaff.setOnClickListener{
            binding.edtStaffCmnd.clearFocus()
            binding.edtNameStaff.clearFocus()
            binding.edtBirthStaff.clearFocus()
            binding.edtPhoneStaff.clearFocus()
            binding.edtEmailStaff.clearFocus()
            binding.edtAddressStaff.clearFocus()
            binding.lnlCustomStaff.hideKeyboard()
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

        bindingDialog.btnAccept.setOnClickListener{
            strAdress = bindingDialog.edtWay.text.toString()+", "+strAdress
            binding.edtAddressStaff.setText(strAdress)
            dialog2.dismiss()
        }

        bindingDialog.btnCancel.setOnClickListener{
            dialog2.dismiss()
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

    override fun OrlError() {
        dialog.showStartDialog3(getString(R.string.staffId_old),this)
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

    override fun updateSuccess() {
        dialog.showStartDialog3(getString(R.string.AddProfileSucces),this)
        binding.btnSave.setText(getString(R.string.tv_save))

        binding.cartStaffCmnd.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtStaffCmnd.setBackground(resources.getDrawable(R.color.gray))
        binding.edtStaffCmnd.isEnabled = false

        binding.cartNameStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtNameStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtNameStaff.isEnabled = false

        binding.cartBirthStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtBirthStaff.setBackground(resources.getDrawable(R.color.gray))

        binding.radioMaleStaff.isEnabled = false
        binding.radioFemaleStaff.isEnabled = false

        binding.cartPhone.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtPhoneStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtPhoneStaff.isEnabled = false

        binding.cartEmailStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtEmailStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtEmailStaff.isEnabled = false

        binding.cartAddress.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtAddressStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtAddressStaff.isEnabled = false
    }

    override fun CheckSuccess() {
        updateStaffPst.updateProfile(reqStaff)
    }

    override fun getListDistrict(list: List<DistrictRes>) {
        listDistrict = list as ArrayList<DistrictRes>
        listDistrictName.add("Tất cả")
        for(i in 0..list.size-1){
            listDistrictName.add(list.get(i).name)
        }
        setAdapterDistrict(listDistrictName)
    }

    fun setAdapterDistrict(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        bindingDialog.spinnerDistrict.setAdapter(adapter)
        bindingDialog.spinnerDistrict.setHint("Tất cả")
        bindingDialog.spinnerWards.setHint("Tất cả")
    }

    override fun getListWards(list: List<WardsRes>) {
        listWardstName.add("Tất cả")
        for(i in 0..list.size-1){
            listWardstName.add(list.get(i).name)
        }
        setAdapterWards(listWardstName)
    }

    fun setAdapterWards(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        bindingDialog.spinnerWards.setAdapter(adapter)
        bindingDialog.spinnerWards.setHint("Tất cả")
    }
}