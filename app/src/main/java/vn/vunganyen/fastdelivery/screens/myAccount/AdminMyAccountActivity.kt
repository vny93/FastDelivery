package vn.vunganyen.fastdelivery.screens.myAccount

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.MD5Hash
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.databinding.ActivityAdminMyAccountBinding

class AdminMyAccountActivity : AppCompatActivity(), AdminMyAccountItf {
    lateinit var binding : ActivityAdminMyAccountBinding
    lateinit var adminMyAccountPst: AdminMyAccountPst
    var dialog : StartAlertDialog = StartAlertDialog()
    var md5 : MD5Hash = MD5Hash()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adminMyAccountPst = AdminMyAccountPst(this)
        setToolbar()
        setEvent()
    }

    fun setEvent(){
        binding.btnChangePass.setOnClickListener{
            var curentPw = md5.md5Code(binding.edtCurrentPass.text.toString())
            var password1 = binding.edtNewPass.text.toString()
            var password2 = binding.edtPass2.text.toString()
            adminMyAccountPst.validCheck(curentPw,password1,password2)
        }
        binding.viewAccount.setOnClickListener({
            binding.edtCurrentPass.clearFocus()
            binding.edtNewPass.clearFocus()
            binding.edtPass2.clearFocus()
            binding.viewAccount.hideKeyboard()
        })
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
        var toolbar = binding.toolbarChangePw
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }


    override fun ErrorIsEmpty() {
        dialog.showStartDialog3(getString(R.string.account_empty),this)
    }

    override fun RegexPassword() {
        dialog.showStartDialog3(getString(R.string.PassIllegal),this)
    }

    override fun ErrorConfirmPw() {
        dialog.showStartDialog3(getString(R.string.account_errorConfirmPw),this)
    }

    override fun ErrorCurrentPw() {
        dialog.showStartDialog3(getString(R.string.account_errorCurrentPw),this)
    }

    override fun ChangePwSuccess() {
        binding.edtCurrentPass.setText("")
        binding.edtNewPass.setText("")
        binding.edtPass2.setText("")
        dialog.showStartDialog3(getString(R.string.account_changePwSuccess),this)
    }

    override fun ChangePwFail() {
        dialog.showStartDialog3(getString(R.string.account_changePwFail),this)
    }
}