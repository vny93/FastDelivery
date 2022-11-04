package vn.vunganyen.fastdelivery.screens.admin.chooseStaff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import vn.vunganyen.fastdelivery.databinding.ActivityChooseStaffMngBinding
import vn.vunganyen.fastdelivery.screens.admin.shipperMng.ShipperMngActivity
import vn.vunganyen.fastdelivery.screens.admin.staffMng.getList.StaffMngActivity

class ChooseStaffMngActivity : AppCompatActivity() {
    lateinit var binding : ActivityChooseStaffMngBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseStaffMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
        setToolbar()
    }

    fun setEvent(){
        binding.btnStaff.setOnClickListener{
            var intent = Intent(this, StaffMngActivity::class.java)
            startActivity(intent)
        }

        binding.btnShipper.setOnClickListener{
            var intent = Intent(this, ShipperMngActivity::class.java)
            startActivity(intent)
        }
    }

    fun setToolbar() {
        var toolbar = binding.toolbarStaffMng
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}