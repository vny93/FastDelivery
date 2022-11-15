package vn.vunganyen.fastdelivery.screens.staff.myProfile

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.role.RoleReq
import vn.vunganyen.fastdelivery.data.model.staff.AdminUpdateReq
import vn.vunganyen.fastdelivery.data.model.staff.StaffUpdateReq
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.databinding.ActivityProfileStSpBinding
import vn.vunganyen.fastdelivery.screens.admin.myProfile.AdminMyProfileActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*

class ProfileStSpActivity : AppCompatActivity(),ProfileStSpItf {
    lateinit var binding : ActivityProfileStSpBinding
    lateinit var profileStSpPst: ProfileStSpPst
    var c = Calendar.getInstance()
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var reqUpdate : StaffUpdateReq
    companion object{
        lateinit var profileStSp : ProfileRes
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileStSpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileStSpPst = ProfileStSpPst(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        profileStSp = SplashActivity.profile.result
        binding.edtStaffId.setText(profileStSp.manv)
        binding.edtNameStaff.setText(profileStSp.hoten)
        binding.edtStaffCmnd.setText(profileStSp.cmnd)
        binding.edtEmailStaff.setText(profileStSp.email)
        binding.edtPhoneStaff.setText(profileStSp.sdt)
        binding.edtAddressStaff.setText(profileStSp.diachi)
        var date : Date = SplashActivity.formatdate.parse(profileStSp.ngaysinh)
        c.time = date
        c.add(Calendar.DATE, 1) // number of days to add
        var strDate = SplashActivity.formatdate1.format(c.time)
        binding.edtBirthStaff.setText(strDate)

        if(profileStSp.gioitinh.equals("Nam")){
            binding.radioMaleStaff.isChecked = true
        }
        else binding.radioFemaleStaff.isChecked = true
        profileStSpPst.getRoleDetail(RoleReq(profileStSp.tendangnhap))
        if(profileStSp.makho != 0){
            profileStSpPst.getDetailWH(GetDetailWHReq(profileStSp.makho))
        }
        else{
            binding.tvStaffWH.visibility = View.GONE
            binding.edtStaffWH.visibility = View.GONE
            binding.cartStaffWH.visibility = View.GONE
        }
    }

    fun setEvent(){
        binding.btnSave.setOnClickListener{
            if(binding.btnSave.text.toString().equals("Cập nhật")){
                binding.btnSave.setText("Lưu")

                binding.cartPhone.setCardBackgroundColor(Color.WHITE)
                binding.edtPhoneStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtPhoneStaff.isEnabled = true

            }
            else{
                var id = binding.edtStaffId.text.toString()
                var phone = binding.edtPhoneStaff.text.toString()

                println("phone: "+phone)
                reqUpdate = StaffUpdateReq(id,phone)
                profileStSpPst.validCheckUpdate(reqUpdate)
            }
        }

        binding.lnlCustomStaff.setOnClickListener{
            binding.edtPhoneStaff.clearFocus()
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

    override fun getDetailWH(res: WarehouseRes) {
        binding.edtStaffWH.setText(res.tenkho)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun PhoneIllegal() {
        dialog.showStartDialog3(getString(R.string.PhoneIllegal),this)
    }

    override fun PhoneExist() {
        dialog.showStartDialog3(getString(R.string.PhoneExist),this)
    }


    override fun updateSuccess() {
        dialog.showStartDialog3(getString(R.string.AddProfileSucces),this)
        binding.btnSave.setText(getString(R.string.tv_save))

        SplashActivity.editor.commit()
        profileStSpPst.getProfileEditor()

        binding.cartPhone.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtPhoneStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtPhoneStaff.isEnabled = false
    }

    override fun CheckSuccess() {
        profileStSpPst.updateProfile(reqUpdate)
    }
}