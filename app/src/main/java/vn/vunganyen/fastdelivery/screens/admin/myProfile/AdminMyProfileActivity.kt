package vn.vunganyen.fastdelivery.screens.admin.myProfile

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.role.RoleReq
import vn.vunganyen.fastdelivery.data.model.staff.AdminUpdateReq
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.databinding.ActivityAdminMyProfileBinding
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class AdminMyProfileActivity : AppCompatActivity(),AdminMyProfileItf {
    lateinit var binding : ActivityAdminMyProfileBinding
    lateinit var adminMyProfilePst: AdminMyProfilePst
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
//    var listDistrict = ArrayList<DistrictRes>()
//    var listDistrictName = ArrayList<String>()
//    var listWardstName = ArrayList<String>()
//    var adress = ""
//    var wards = ""
//    var strAdress = ""
    lateinit var reqUpdate : AdminUpdateReq
    companion object{
        lateinit var profileAd : ProfileRes
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adminMyProfilePst = AdminMyProfilePst(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        profileAd = SplashActivity.profile.result
        binding.edtStaffId.setText(profileAd.manv)
        binding.edtNameStaff.setText(profileAd.hoten)
        binding.edtStaffCmnd.setText(profileAd.cmnd)
        binding.edtEmailStaff.setText(profileAd.email)
        binding.edtPhoneStaff.setText(profileAd.sdt)
        binding.edtAddressStaff.setText(profileAd.diachi)
        var date : Date = SplashActivity.formatdate.parse(profileAd.ngaysinh)
        c.time = date
        c.add(Calendar.DATE, 1) // number of days to add
        var strDate = SplashActivity.formatdate1.format(c.time)
        binding.edtBirthStaff.setText(strDate)

        if(profileAd.gioitinh.equals("Nam")){
            binding.radioMaleStaff.isChecked = true
        }
        else binding.radioFemaleStaff.isChecked = true
        adminMyProfilePst.getRoleDetail(RoleReq(profileAd.tendangnhap))
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
                reqUpdate = AdminUpdateReq(id,cmnd,name,gender,strDate,phone,email,address)
                adminMyProfilePst.validCheckUpdate(reqUpdate)
            }
        }

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

    override fun getRoleDetail(res: ListRoleRes) {
        binding.edtStaffRole.setText(res.tenquyen)
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

        SplashActivity.editor.commit()
        adminMyProfilePst.getProfileEditor()

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
        adminMyProfilePst.updateProfile(reqUpdate)
    }
}