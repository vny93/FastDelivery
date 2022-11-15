package vn.vunganyen.fastdelivery.screens.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import vn.vunganyen.fastdelivery.data.model.profile.MainProfileRes
import vn.vunganyen.fastdelivery.data.model.profile.ProfileRes
import vn.vunganyen.fastdelivery.databinding.ActivitySplashBinding
import vn.vunganyen.fastdelivery.screens.admin.home.HomeAdminActivity
import vn.vunganyen.fastdelivery.screens.login.LoginActivity
import vn.vunganyen.fastdelivery.screens.shipper.home.HomeShipperActivity
import vn.vunganyen.fastdelivery.screens.staff.home.HomeStaffActivity
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    companion object{
        var ADMIN = 1
        var STAFF = 2
        var SHIPPER = 3
        var STORE = 4
        var API_RPOFILE = "scooter"//"car" //"scooter"
        var API_LOCALE = "vn"
        var API_CALC_POINTS = false
        var API_KEY = "03b225ae-3ff7-40d9-86ee-901bc4347172"
        //Tên đăng nhập tối thiểu tám ký tự, ít nhất một chữ cái và một số
        var USERNAME = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        var EMAIL_ADDRESS = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        //Mât khẩu tối thiểu tám ký tự, ít nhất một chữ cái viết hoa, một chữ cái viết thường và một số
        var PASSWORD = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}\$")
        var SDT = Pattern.compile("(84|0){1}(3|5|7|8|9){1}+([0-9]{8})")
        var CMND = Pattern.compile("[0-9]{9}")
        lateinit var profile: MainProfileRes
        var token: String = ""
        var roleId: Int = 0
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        val formatterPrice = DecimalFormat("###,###,###")
        val formatdate = SimpleDateFormat("yyyy-MM-dd")
        val formatdate1 = SimpleDateFormat("dd/MM/yyyy")
        val formatdate2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH)
        val formatdate3 = SimpleDateFormat("dd-MM-yyyy hh:mm")
        val formatdate4 = SimpleDateFormat("dd-MM-yyyy")
        val formatter = DecimalFormat("###,###,###")
        val formatMonthYear = SimpleDateFormat("M-yyyy")
        val formatMonth = SimpleDateFormat("MM")
        val formatYear = SimpleDateFormat("yyyy")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPreferences()
        //
//        editor.clear().apply()
//        sharedPreferences.edit().clear().apply()
//        println("đã xóa")
        //
        checkShaharedPre()
    }

    private fun initPreferences(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
    }

    fun checkShaharedPre(){
        var roleIdEditor = sharedPreferences.getInt("roleId", 0)
        println("mã quyền lúc đầu: " + roleIdEditor)
        if(roleIdEditor != 0){
            roleId = roleIdEditor
            if (roleId == ADMIN) {
                println("Vô admin nha")
                moveAdmin()
            } else if (roleId == STAFF) {
                println("Vô Admin nha")
                moveStaff()
            }
            else if (roleId == SHIPPER) {
                println("Vô Shipper nha")
                moveShipper()
            }
            else if (roleId == STORE) {
                println("Vô Store nha")
                moveStore()
            }
        }
        else{
            moveLogin()
        }
    }

    fun moveLogin(){
        Handler().postDelayed({
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    fun moveAdmin() {
        Handler().postDelayed({
            var intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    fun moveStaff() {
        Handler().postDelayed({
            var intent = Intent(this, HomeStaffActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    fun moveShipper() {
        Handler().postDelayed({
            var intent = Intent(this, HomeShipperActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    fun moveStore() {
        Handler().postDelayed({
//            var intent = Intent(this, MainShipperActivity::class.java)
//            startActivity(intent)
        }, 3000)
    }

    override fun onResume() {
        super.onResume()
        checkShaharedPre()
    }
}