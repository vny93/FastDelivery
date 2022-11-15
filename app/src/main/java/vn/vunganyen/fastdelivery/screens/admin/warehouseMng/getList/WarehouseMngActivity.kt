package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.getList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterWarehouseMng
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.databinding.ActivityWarehouseMngBinding
import vn.vunganyen.fastdelivery.screens.admin.warehouseMng.insert.InsertWarehouseActivity

class WarehouseMngActivity : AppCompatActivity(), WarehouseMngItf {
    lateinit var binding : ActivityWarehouseMngBinding
    lateinit var warehouseMngPst: WarehouseMngPst
    var adapterWarehouseMng : AdapterWarehouseMng = AdapterWarehouseMng()
    var alertDialog: StartAlertDialog = StartAlertDialog()
    companion object{
        var listWarehouse = ArrayList<WarehouseRes>()
        lateinit var listFilter : ArrayList<WarehouseRes>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarehouseMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        warehouseMngPst = WarehouseMngPst(this)
        setToolbar()
        getData()
        setEvent()
        invokeDeleteWH()
    }

    fun getData(){
        warehouseMngPst.getListWarehouse()
    }

    fun setEvent(){
        binding.imvInsertWH.setOnClickListener{
                var intent = Intent(this, InsertWarehouseActivity::class.java)
                startActivity(intent)
        }

        binding.edtSearchWH.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var str = binding.edtSearchWH.text.toString()
                warehouseMngPst.getFilter(str)
            }
        })

        binding.viewShopMng.setOnClickListener{
            binding.edtSearchWH.clearFocus()
            binding.viewShopMng.hideKeyboard()
        }
        binding.rcvListWH.setOnClickListener{
            binding.edtSearchWH.clearFocus()
            binding.rcvListWH.hideKeyboard()
        }
    }

    fun invokeDeleteWH(){
        adapterWarehouseMng.clickRemoveWarehouse = {
            id -> warehouseMngPst.checkWarehouseUse(GetDetailWHReq(id.toInt()))
        }
    }

    override fun getListWarehouse(list: List<WarehouseRes>) {
        adapterWarehouseMng.setData(list)
        binding.rcvListWH.adapter = adapterWarehouseMng
        binding.rcvListWH.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun deleteFail() {
        alertDialog.showStartDialog3(getString(R.string.RemoveWHFail),this)
    }

    override fun allowDetele(req: GetDetailWHReq) {
        alertDialog.showStartDialog4(getString(R.string.AllRemoveWH),this)
        alertDialog.clickOk = {
            ->warehouseMngPst.deleteWarehouse(req)
        }
    }

    override fun deleteSuccess() {
        alertDialog.showStartDialog3(getString(R.string.RemoveStaffSuccess),this)
        getData()
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
        var toolbar = binding.toolbarListWH
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        warehouseMngPst.getListWarehouse()
    }
}