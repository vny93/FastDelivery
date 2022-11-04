package vn.vunganyen.fastdelivery.screens.shipper.home

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.databinding.ActivityHomeShipperBinding
import vn.vunganyen.fastdelivery.screens.account.FragmentAccount
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity


class HomeShipperActivity : AppCompatActivity(), HomeShipperItf {
    lateinit var binding: ActivityHomeShipperBinding
    lateinit var homeShipperPst: HomeShipperPst
    var backPressed : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeShipperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeShipperPst = HomeShipperPst(this)
        checkShaharedPre()
        setEventBottomNav()
     //   replaceFragment(FragmentShop())
    }

    fun checkShaharedPre(){
        var tokenEditor = SplashActivity.sharedPreferences.getString("token", "").toString()
        println("token lúc đầu: "+tokenEditor)
        if(!tokenEditor.equals("")){
            SplashActivity.token = tokenEditor
            homeShipperPst.getProfileEditor()
        }
    }


    fun setEventBottomNav() {
        binding.bottomNav2.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_order2 -> {
                  //  replaceFragment(FragmentShop())
                    Toast.makeText(this, "Home Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_money2 -> {
                 //   replaceFragment(FragmentExplore())
                    Toast.makeText(this, "Search Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_area -> {
                //    replaceFragment(FragmentCart())
                    Toast.makeText(this, "Cart Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_account2 -> {
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
        transaction.replace(R.id.content_frame2, fragment)
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