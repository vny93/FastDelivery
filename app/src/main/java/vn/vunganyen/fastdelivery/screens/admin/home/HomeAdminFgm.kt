package vn.vunganyen.fastdelivery.screens.admin.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.fastdelivery.databinding.FgmHomeAdminBinding
import vn.vunganyen.fastdelivery.screens.admin.parcelMng.assignment.AssignmentMngActivity
import vn.vunganyen.fastdelivery.screens.admin.priceList.PriceActivity
import vn.vunganyen.fastdelivery.screens.admin.staffMng.getList.StaffMngActivity


class HomeAdminFgm : Fragment() {
    lateinit var binding : FgmHomeAdminBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FgmHomeAdminBinding.inflate(inflater,container,false)
        setData()
        setEvent()
        return binding.root
    }

    fun setData(){
      //  binding.homeNameAdmin.text = SplashScreenActivity.profileAdmin.result.hoten
    }

    fun setEvent(){
        binding.imvStaffMng.setOnClickListener{
            var intent = Intent(context, StaffMngActivity::class.java)
            startActivity(intent)
        }
        binding.imvWarehouseMng.setOnClickListener{

        }
        binding.imvStoreMng.setOnClickListener{

        }
        binding.imvParcelMng.setOnClickListener{
            var intent = Intent(context, AssignmentMngActivity::class.java)
            startActivity(intent)
        }
        binding.imvPriceMng.setOnClickListener{
            var intent = Intent(context, PriceActivity::class.java)
            startActivity(intent)
        }
        binding.imvStatistics.setOnClickListener{

        }
    }

    override fun onResume() {
        super.onResume()
      //  binding.homeNameAdmin.text = SplashScreenActivity.profileAdmin.result.hoten
    }

}