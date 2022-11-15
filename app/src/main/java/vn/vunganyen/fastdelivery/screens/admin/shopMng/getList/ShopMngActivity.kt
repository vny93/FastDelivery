package vn.vunganyen.fastdelivery.screens.admin.shopMng.getList

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.data.adapter.AdapterShopMng
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.databinding.ActivityShopMngBinding

class ShopMngActivity : AppCompatActivity(), ShopMngItf {
    lateinit var binding : ActivityShopMngBinding
    lateinit var shopMngPst: ShopMngPst
    var adapterShopMng : AdapterShopMng = AdapterShopMng()
    var alertDialog: StartAlertDialog = StartAlertDialog()
    companion object{
        var listShop = ArrayList<GetShopDetailRes>()
        lateinit var listFilter : ArrayList<GetShopDetailRes>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shopMngPst = ShopMngPst(this)
        setToolbar()
        getData()
        setEvent()
    }

    fun getData(){
        shopMngPst.getListShop()
    }

    fun setEvent(){
        binding.edtSearchShop.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var str = binding.edtSearchShop.text.toString()
                shopMngPst.getFilter(str)
            }
        })

        binding.viewShopMng.setOnClickListener{
            binding.edtSearchShop.clearFocus()
            binding.viewShopMng.hideKeyboard()
        }
        binding.rcvListShop.setOnClickListener{
            binding.edtSearchShop.clearFocus()
            binding.rcvListShop.hideKeyboard()
        }
    }

    override fun getListShop(list: List<GetShopDetailRes>) {
        adapterShopMng.setData(list)
        binding.rcvListShop.adapter = adapterShopMng
        binding.rcvListShop.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }

    fun setToolbar() {
        var toolbar = binding.toolbarListShop
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}