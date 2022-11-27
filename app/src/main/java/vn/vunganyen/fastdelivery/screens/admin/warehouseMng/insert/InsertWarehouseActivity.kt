package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.insert

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.warehouse.InsertWHReq
import vn.vunganyen.fastdelivery.databinding.ActivityInsertWarehouseBinding

class InsertWarehouseActivity : AppCompatActivity(),InsertWarehouseItf {
    lateinit var binding : ActivityInsertWarehouseBinding
    lateinit var insertWarehousePst: InsertWarehousePst
    var dialog: StartAlertDialog = StartAlertDialog()
    var wards = ""
    var district=""
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertWarehouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertWarehousePst = InsertWarehousePst(this)
        getData()
        setEvent()
        setToolbar()
    }

    fun getData(){
        insertWarehousePst.getDistrict()
    }

    fun setEvent(){
        binding.spinnerDistrict.setOnItemClickListener(({ adapterView, view, i, l ->
            district = adapterView.getItemAtPosition(i).toString()
            for (list in listDistrict) {
                if (list.name.equals(adapterView.getItemAtPosition(i).toString())) {
                    println("code:" + list.code)
                    insertWarehousePst.getWards(list.code)
                }
            }
            println("district: " + district)
        }))

        binding.spinnerWards.setOnItemClickListener(({ adapterView, view, i, l ->
            wards = adapterView.getItemAtPosition(i).toString()
            println("wards: " + wards)
        }))

        binding.btnSave.setOnClickListener{
            var name = binding.edtWarehouseName.text.toString()
            var way = binding.edtWarehouseWay.text.toString()
            if(wards.equals("") || district.equals("") || way.equals("")){
                dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.error_empty),this)
            }
            else{
                var adress = binding.edtWarehouseWay.text.toString() + ", "+ wards + ", " + district +", Thành phố Hồ Chí Minh"
                var req = InsertWHReq(name,adress)
                insertWarehousePst.constraintCheck(req)
            }
        }

        binding.lnlCustomStaff.setOnClickListener{
            binding.edtWarehouseName.clearFocus()
            binding.edtWarehouseWay.clearFocus()
            binding.lnlCustomStaff.hideKeyboard()
        }
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
        var toolbar = binding.toolbarInsertStaff
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getListDistrict(list: List<DistrictRes>) {
        listDistrict = list as ArrayList<DistrictRes>
        for (i in 0..list.size - 1) {
            listDistrictName.add(list.get(i).name)
        }
        setAdapterDistrict(listDistrictName)
    }

    fun setAdapterDistrict(list: List<String>) {
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerDistrict.setAdapter(adapter)
    }

    override fun getListWards(list: List<WardsRes>) {
        for (i in 0..list.size - 1) {
            listWardstName.add(list.get(i).name)
        }
        setAdapterWards(listWardstName)
    }

    fun setAdapterWards(list: List<String>) {
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerWards.setAdapter(adapter)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.error_empty),this)
    }

    override fun AddSucces() {
        dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.tv_rgt_WHSuccess),this)
        binding.edtWarehouseName.setText("")
        binding.edtWarehouseWay.setText("")
        binding.spinnerDistrict.setText("",false)
        binding.spinnerWards.setText("",false)
    }

}