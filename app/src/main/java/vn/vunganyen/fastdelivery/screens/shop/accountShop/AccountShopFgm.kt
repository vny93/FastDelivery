package vn.vunganyen.fastdelivery.screens.shop.accountShop

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.databinding.FragmentAccountShopFgmBinding
import vn.vunganyen.fastdelivery.screens.login.LoginActivity
import vn.vunganyen.fastdelivery.screens.myAccount.AdminMyAccountActivity
import vn.vunganyen.fastdelivery.screens.shop.changePassword.ShopChangePwActivity
import vn.vunganyen.fastdelivery.screens.shop.profileShop.ProfileShopActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.myProfile.ProfileStSpActivity

class AccountShopFgm : Fragment() {
    lateinit var binding : FragmentAccountShopFgmBinding
    var dialog: StartAlertDialog = StartAlertDialog()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountShopFgmBinding.inflate(layoutInflater)
        setEvent()
        setData()
        return binding.root
    }

    fun setData() {
        if (!SplashActivity.token.equals("")) {
            binding.tvHeaderName.text = SplashActivity.profileShop.result.tench
            binding.tvHeaderUser.text = SplashActivity.profileShop.result.tendangnhap
        }
    }

    fun setEvent() {
        binding.btnLogOut.setOnClickListener {
            context?.let { it1 -> dialog.showStartDialog4(getString(R.string.mess_logOut), it1) }
            dialog.clickOk = { ->
                SplashActivity.token = ""
                SplashActivity.editor.clear().apply()
                SplashActivity.sharedPreferences.edit().clear().apply()
                println("đã xóa")
                var intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        binding.imvInfor.setOnClickListener {
            var intent = Intent(context, ProfileShopActivity::class.java)
            startActivity(intent)
        }


        binding.imvChangePass.setOnClickListener {
            var intent = Intent(context, ShopChangePwActivity::class.java)
            startActivity(intent)

        }

        binding.imvHelp.setOnClickListener {
            dialog.clickOk = { ->
//                var intent = Intent(context, LoginActivity::class.java)
//                startActivity(intent)
            }
        }

    }


}