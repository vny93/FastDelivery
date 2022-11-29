package vn.vunganyen.fastdelivery.screens.registerShop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.fastdelivery.databinding.ActivityShopRegisterBinding

class ShopRegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityShopRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}