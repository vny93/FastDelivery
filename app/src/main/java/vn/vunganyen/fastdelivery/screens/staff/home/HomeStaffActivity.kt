package vn.vunganyen.fastdelivery.screens.staff.home

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.databinding.ActivityHomeStaffBinding
import vn.vunganyen.fastdelivery.screens.account.FragmentAccount
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.parcelMng.StaffParceFgm


class HomeStaffActivity : AppCompatActivity(), HomeStaffItf {
    lateinit var binding: ActivityHomeStaffBinding
    lateinit var homeStaffPst: HomeStaffPst
    var backPressed : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeStaffPst = HomeStaffPst(this)
        checkShaharedPre()
        setEventBottomNav()
        replaceFragment(StaffParceFgm())
        setEvent()
    }

    fun checkShaharedPre(){
        var tokenEditor = SplashActivity.sharedPreferences.getString("token", "").toString()
        println("token lúc đầu: "+tokenEditor)
        if(!tokenEditor.equals("")){
            SplashActivity.token = tokenEditor
            homeStaffPst.getProfileEditor()
        }
    }

    fun setEvent(){


    }


    fun setEventBottomNav() {
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_order -> {
                    replaceFragment(StaffParceFgm())
                    Toast.makeText(this, "Home Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_money -> {
                 //   replaceFragment(FragmentExplore())
                    Toast.makeText(this, "Search Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
              //  R.id.action_cart -> {
                  //  replaceFragment(FragmentCart())
              //      Toast.makeText(this, "Cart Item reselected", Toast.LENGTH_SHORT).show()
              //      true
             //   }
                R.id.action_account -> {
                    replaceFragment(FragmentAccount())
                    Toast.makeText(this, "Account Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame1, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            moveTaskToBack(true);
            // System.exit(1);
            finish();
        }
        else{
            Toast.makeText(this,"Press back again to exit the application",Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }
}