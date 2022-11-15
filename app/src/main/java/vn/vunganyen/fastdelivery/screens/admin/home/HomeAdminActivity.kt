package vn.vunganyen.fastdelivery.screens.admin.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.databinding.ActivityHomeAdminBinding
import vn.vunganyen.fastdelivery.databinding.HeaderNaviBinding
import vn.vunganyen.fastdelivery.screens.myAccount.AdminMyAccountActivity
import vn.vunganyen.fastdelivery.screens.admin.myProfile.AdminMyProfileActivity
import vn.vunganyen.fastdelivery.screens.login.LoginActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.petshop.screens.admin.main.HomeAdminItf
import vn.vunganyen.petshop.screens.admin.main.HomeAdminPst


class HomeAdminActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,HomeAdminItf{
    lateinit var binding: ActivityHomeAdminBinding
    lateinit var toogle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var homeAdminPst: HomeAdminPst
    lateinit var headerBinding: HeaderNaviBinding
    var backPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeAdminPst = HomeAdminPst(this)
        checkShaharedPre()
        setNavigationView()
    }

    fun checkShaharedPre(){
        var tokenEditor = SplashActivity.sharedPreferences.getString("token", "").toString()
        println("token lúc đầu: "+tokenEditor)
        if(!tokenEditor.equals("")){
            SplashActivity.token = tokenEditor
            homeAdminPst.getProfileEditor()
        }
    }

    fun setNavigationView() {
        val toolbar: Toolbar = binding.adToolbarMain
        drawerLayout = binding.drawerLayout
        toogle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        var navigationView: NavigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener(this)
        replaceFragment(HomeAdminFgm())
        navigationView.menu.findItem(R.id.nax_1).setCheckable(true)
        headerBinding= HeaderNaviBinding.bind(binding.navigationView.getHeaderView(0))

        headerBinding.tvadHeaderName.text = SplashActivity.profile.result.hoten
        headerBinding.tvadHeaderUser.text = SplashActivity.profile.result.tendangnhap


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.nax_1) {
            replaceFragment(HomeAdminFgm())
        } else if (id == R.id.nax_2) {
            var intent = Intent(this, AdminMyProfileActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nax_3) {
              var intent = Intent(this, AdminMyAccountActivity::class.java)
              startActivity(intent)
        } else if (id == R.id.nax_4) {
            dialog.showStartDialog4(getString(R.string.mess_logOut), this)
            dialog.clickOk = { ->
                println("Vô đây")
                SplashActivity.token = ""
                SplashActivity.editor.clear().apply()
                SplashActivity.sharedPreferences.edit().clear().apply()
                println("đã xóa")
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.ad_content_frame, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            moveTaskToBack(true);
            // System.exit(1);
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit the application", Toast.LENGTH_SHORT)
                .show()
        }
        backPressed = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        headerBinding.tvadHeaderName.text = SplashActivity.profile.result.hoten
    }


}