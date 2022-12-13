package vn.vunganyen.fastdelivery.screens.forgotPassword

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.mail.MailReq
import vn.vunganyen.fastdelivery.data.model.profile.ProfileReq
import vn.vunganyen.fastdelivery.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity(),ForgotPasswordItf {
    lateinit var binding : ActivityForgotPasswordBinding
    lateinit var forgotPasswordPst: ForgotPasswordPst
    var dialog: StartAlertDialog = StartAlertDialog()
    var user = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        forgotPasswordPst = ForgotPasswordPst(this)
        setToolbar()
        setEvent()
    }

    fun setEvent(){
        binding.btnCancel.setOnClickListener{
            finish()
        }
        binding.tvLogin.setOnClickListener{
            finish()
        }
        binding.btnFind.setOnClickListener{
            if(binding.edtUsername.hint.equals("Nhập tên đăng nhập")){
                user = binding.edtUsername.text.toString().trim()
                forgotPasswordPst.checkUser(ProfileReq(user))
            }
            else{
                var email = binding.edtUsername.text.toString().trim()
                forgotPasswordPst.checkEmail(MailReq(email,user))
            }
        }

        binding.lnlForgot.setOnClickListener{
            binding.edtUsername.clearFocus()
            binding.lnlForgot.hideKeyboard()
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
        var toolbar = binding.toolbarAdminParcel
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun AccountNotExist() {
        dialog.showStartDialog3(getString(R.string.Account_not_exist),this)
    }

    override fun AccountExist(req: ProfileReq) {
        binding.btnFind.setText("Gửi")
        binding.imvForgot.setBackgroundResource(R.drawable.ic_baseline_email_24)
        binding.edtUsername.setText("")
        binding.edtUsername.hint = "Nhập email"
        binding.tvForgot1.setText(getString(R.string.find_account4))
        binding.tvForgot2.setText(getString(R.string.find_account3))
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun EmailIllegal() {
        dialog.showStartDialog3(getString(R.string.EmailIllegal),this)
    }

    override fun sendSuccess() {
        dialog.showStartDialog3(getString(R.string.send_mail_success),this)
        binding.edtUsername.setText("")
    }
}