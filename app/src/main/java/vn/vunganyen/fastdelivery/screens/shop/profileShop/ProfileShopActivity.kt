package vn.vunganyen.fastdelivery.screens.shop.profileShop

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
import vn.vunganyen.fastdelivery.data.model.role.RoleReq
import vn.vunganyen.fastdelivery.data.model.shop.AddShopReq
import vn.vunganyen.fastdelivery.data.model.shop.UpdatePhoneShopReq
import vn.vunganyen.fastdelivery.data.model.staff.StaffUpdateReq
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.databinding.ActivityProfileShopBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.myProfile.ProfileStSpActivity
import java.util.*

class ProfileShopActivity : AppCompatActivity(), ProfileShopItf {
    lateinit var binding : ActivityProfileShopBinding
    lateinit var profileShopPst: ProfileShopPst
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var reqUpdate : UpdatePhoneShopReq
    companion object{
        lateinit var profileShop : AddShopReq
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileShopPst = ProfileShopPst(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        profileShop = SplashActivity.profileShop.result
        binding.edtShopId.setText(profileShop.mach)
        binding.edtNameShop.setText(profileShop.tench)
        binding.edtEmailShop.setText(profileShop.email)
        binding.edtPhoneShop.setText(profileShop.sdt)
        binding.edtAddressShop.setText(profileShop.diachi)
    }

    fun setEvent(){
        binding.btnSave.setOnClickListener{
            if(binding.btnSave.text.toString().equals("Cập nhật")){
                binding.btnSave.setText("Lưu")

                binding.cartPhone.setCardBackgroundColor(Color.WHITE)
                binding.edtPhoneShop.setBackground(resources.getDrawable(R.color.white))
                binding.edtPhoneShop.isEnabled = true

            }
            else{
                var id = binding.edtShopId.text.toString()
                var phone = binding.edtPhoneShop.text.toString()
                println("phone: "+phone)
                reqUpdate = UpdatePhoneShopReq(phone,id)
                profileShopPst.validCheckUpdate(reqUpdate)
            }
        }

        binding.lnlCustomStaff.setOnClickListener{
            binding.edtPhoneShop.clearFocus()
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
        profileShopPst.getProfileEditor()

        binding.cartPhone.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtPhoneShop.setBackground(resources.getDrawable(R.color.gray))
        binding.edtPhoneShop.isEnabled = false
    }

    override fun CheckSuccess() {
        profileShopPst.updateProfileShop(reqUpdate)
    }
}