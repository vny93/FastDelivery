package vn.vunganyen.fastdelivery.screens.shop.homeShop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.databinding.ActivityHomeShopBinding
import vn.vunganyen.fastdelivery.screens.account.FragmentAccount
import vn.vunganyen.fastdelivery.screens.shipper.parcelSpMng.ShipperParcelFgm
import vn.vunganyen.fastdelivery.screens.shipper.registerArea.RegisterAreaFgm
import vn.vunganyen.fastdelivery.screens.shipper.salary.ShipperSalaryFgm
import vn.vunganyen.fastdelivery.screens.shop.accountShop.AccountShopFgm
import vn.vunganyen.fastdelivery.screens.shop.parcelShop.ShopParcelFgm
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class HomeShopActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeShopBinding
    lateinit var homeShopPst: HomeShopPst
    var backPressed : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeShopPst = HomeShopPst()
        checkShaharedPre()
        setEventBottomNav()
        replaceFragment(ShopParcelFgm())
    }

    fun checkShaharedPre(){
        var tokenEditor = SplashActivity.sharedPreferences.getString("token", "").toString()
        println("token lúc đầu: "+tokenEditor)
        if(!tokenEditor.equals("")){
            SplashActivity.token = tokenEditor
            homeShopPst.getProfileEditor()
        }
    }

    fun setEventBottomNav() {
        binding.bottom3Nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_order3 -> {
                    replaceFragment(ShopParcelFgm())
                    true
                }
                R.id.action_account3 -> {
                    replaceFragment(AccountShopFgm())
                    true
                }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame3, fragment)
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
            Toast.makeText(this,"Press back again to exit the application", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }
}