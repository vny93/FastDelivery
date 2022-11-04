package vn.vunganyen.fastdelivery.screens.admin.staffMng.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.fastdelivery.databinding.ActivityUpdateStaffBinding

class UpdateStaffActivity : AppCompatActivity(),UpdateStaffItf {
    lateinit var binding : ActivityUpdateStaffBinding
    lateinit var updateStaffPst: UpdateStaffPst
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateStaffPst = UpdateStaffPst(this)
    }
}