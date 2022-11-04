package vn.vunganyen.fastdelivery.screens.admin.shipperMng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import vn.vunganyen.fastdelivery.databinding.ActivityShipperMngBinding

class ShipperMngActivity : AppCompatActivity() {
    lateinit var binding : ActivityShipperMngBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipperMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
    }

    fun setToolbar() {
        var toolbar = binding.toolbarListShipper
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}