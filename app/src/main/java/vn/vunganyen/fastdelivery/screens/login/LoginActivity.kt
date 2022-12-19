package vn.vunganyen.fastdelivery.screens.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterPhoto
import vn.vunganyen.fastdelivery.data.model.classSupport.MD5Hash
import vn.vunganyen.fastdelivery.data.model.classSupport.Photo
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.databinding.ActivityLoginBinding
import vn.vunganyen.fastdelivery.screens.admin.home.HomeAdminActivity
import vn.vunganyen.fastdelivery.screens.forgotPassword.ForgotPasswordActivity
import vn.vunganyen.fastdelivery.screens.shipper.home.HomeShipperActivity
import vn.vunganyen.fastdelivery.screens.shop.homeShop.HomeShopActivity
import vn.vunganyen.fastdelivery.screens.shop.registerShop.ShopRegisterActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.home.HomeStaffActivity
import java.util.*
import kotlin.concurrent.timerTask

class LoginActivity : AppCompatActivity(), LoginItf {
    lateinit var binding : ActivityLoginBinding
    lateinit var loginPst: LoginPst
    var backPressed : Long = 0
    var dialog : StartAlertDialog = StartAlertDialog()
    lateinit var photoAdapter : AdapterPhoto
    lateinit var listPhoto : List<Photo>
    var time : Timer = Timer()
    var md5 : MD5Hash = MD5Hash()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginPst = LoginPst(this)
        autoSlideImage()
        setEvent()
    }

    fun setEvent(){
        binding.btnLogin.setOnClickListener{
            var username = binding.edtUsername.text.toString().toLowerCase().trim()
            var password = md5.md5Code(binding.edtPassword.text.toString())
            loginPst.checkEmpty(username,password)
        }

        binding.btnRegister.setOnClickListener{
            var intent = Intent(this, ShopRegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPassword.setOnClickListener{
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.scrollViewLogin.setOnClickListener{
            binding.edtUsername.clearFocus()
            binding.edtPassword.clearFocus()
            binding.scrollViewLogin.hideKeyboard()
        }

    }

    fun autoSlideImage(){
        listPhoto = getListData()
        photoAdapter = AdapterPhoto(applicationContext,listPhoto)
        binding.viewPager.adapter = photoAdapter
        binding.circleIndicator.setViewPager(binding.viewPager)
        if(listPhoto == null || listPhoto.isEmpty() || binding.viewPager == null){
            return
        }
        if(time == null){
            time = Timer()
        }
        time.schedule(timerTask {
            Handler(Looper.getMainLooper()).post(Runnable(){
                var currenItem = binding.viewPager.currentItem
                var totalItem = listPhoto.size-1
                if(currenItem < totalItem){
                    currenItem++
                    binding.viewPager.setCurrentItem(currenItem)
                }else{
                    binding.viewPager.setCurrentItem(0)
                }
            })
        }, 500,3000)
    }

    fun getListData(): List<Photo>{
        var list = ArrayList<Photo>()
        list.add(Photo(R.drawable.pn1))
        list.add(Photo(R.drawable.pn5))
        list.add(Photo(R.drawable.pn6))
        list.add(Photo(R.drawable.pn4))
        return list
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

    override fun onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            moveTaskToBack(true)
            finish();
        }
        else{
            Toast.makeText(this,"Press back again to exit the application", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    override fun loginEmpty() {
        dialog.showStartDialog3(getString(R.string.login_empty), this)
    }

    override fun loginFail() {
        dialog.showStartDialog3(getString(R.string.wrong_account), this)
    }

    override fun loginLock() {
        dialog.showStartDialog3(getString(R.string.lock_account), this)
    }

    override fun loginSuccess() {
        SplashActivity.editor.commit()
        if(SplashActivity.roleId == SplashActivity.ADMIN){
            var intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)
        }
        else if(SplashActivity.roleId == SplashActivity.STAFF){
            var intent = Intent(this, HomeStaffActivity::class.java)
            startActivity(intent)
        }
        else if(SplashActivity.roleId == SplashActivity.SHIPPER){
            var intent = Intent(this, HomeShipperActivity::class.java)
            startActivity(intent)
        }
        else{
            var intent = Intent(this, HomeShopActivity::class.java)
            startActivity(intent)
        }
    }
}