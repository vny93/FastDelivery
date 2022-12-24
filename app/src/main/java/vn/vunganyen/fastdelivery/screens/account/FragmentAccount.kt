package vn.vunganyen.fastdelivery.screens.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.databinding.FragmentAccountBinding
import vn.vunganyen.fastdelivery.screens.login.LoginActivity
import vn.vunganyen.fastdelivery.screens.myAccount.AdminMyAccountActivity
import vn.vunganyen.fastdelivery.screens.shipper.barcode.ScanBarCodeActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.myProfile.ProfileStSpActivity


class FragmentAccount : Fragment() {
    lateinit var binding: FragmentAccountBinding
    var dialog: StartAlertDialog = StartAlertDialog()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        setEvent()
        setData()
        return binding.root
    }

    fun setData() {
        if (!SplashActivity.token.equals("")) {
            binding.tvHeaderName.text = SplashActivity.profile.result.hoten
            binding.tvHeaderUser.text = SplashActivity.profile.result.tendangnhap
        }
        if(SplashActivity.profile.result.makho != 0){
            binding.lnlScan.visibility = View.GONE
            binding.tvScan.visibility = View.GONE
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
            var intent = Intent(context, ProfileStSpActivity::class.java)
            startActivity(intent)
        }


        binding.imvChangePass.setOnClickListener {
            var intent = Intent(context, AdminMyAccountActivity::class.java)
            startActivity(intent)

        }

        binding.imvHelp.setOnClickListener {
            dialog.clickOk = { ->
//                var intent = Intent(context, LoginActivity::class.java)
//                startActivity(intent)
            }
        }
        binding.imvScanImv.setOnClickListener{
            var intent = Intent(context, ScanBarCodeActivity::class.java)
            startActivity(intent)
        }

    }

}