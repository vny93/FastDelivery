package vn.vunganyen.fastdelivery.screens.admin.warehouseMng.update

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.data.model.warehouse.InsertWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.databinding.ActivityUpdateWarehouseBinding

class UpdateWarehouseActivity : AppCompatActivity(), UpdateWarehouseItf {
    lateinit var binding : ActivityUpdateWarehouseBinding
    lateinit var updateWarehousePst: UpdateWarehousePst
    var dialog: StartAlertDialog = StartAlertDialog()
    var wards = ""
    var district=""
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()

    companion object{
        lateinit var warehouse : WarehouseRes
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateWarehouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateWarehousePst = UpdateWarehousePst(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        warehouse = getIntent().getSerializableExtra("data") as WarehouseRes
        binding.edtWarehouseName.setText(warehouse.tenkho)
        var str = warehouse.diachi.trim()
        val arrWord = str.split(", ")
        binding.edtWarehouseWay.setText(arrWord[0])
        binding.spinnerWards.setText(arrWord[1])
        wards = arrWord[1]
        binding.spinnerDistrict.setText(arrWord[2])
        district = arrWord[2]
    }

    fun setEvent() {
        binding.spinnerDistrict.setOnItemClickListener(({ adapterView, view, i, l ->
            district = adapterView.getItemAtPosition(i).toString()
            for (list in listDistrict) {
                if (list.name.equals(adapterView.getItemAtPosition(i).toString())) {
                    println("code:" + list.code)
                    updateWarehousePst.getWards(list.code)
                }
            }
            println("district: " + district)
        }))

        binding.spinnerWards.setOnItemClickListener(({ adapterView, view, i, l ->
            wards = adapterView.getItemAtPosition(i).toString()
            println("wards: " + wards)
        }))
        binding.btnSave.setOnClickListener {
            if (binding.btnSave.text.toString().equals("Cập nhật")) {
                binding.btnSave.setText("Lưu")

                updateWarehousePst.getDistrict()

                binding.cartName.setCardBackgroundColor(Color.WHITE)
                binding.edtWarehouseName.setBackground(resources.getDrawable(R.color.white))
                binding.edtWarehouseName.isEnabled = true

                binding.cartWay.setCardBackgroundColor(Color.WHITE)
                binding.edtWarehouseWay.setBackground(resources.getDrawable(R.color.white))
                binding.edtWarehouseWay.isEnabled = true

                binding.cartSpinnerDistric.setCardBackgroundColor(Color.WHITE)
                binding.spinnerDistrict.setBackground(resources.getDrawable(R.color.white))

                binding.cartSpinnerWards.setCardBackgroundColor(Color.WHITE)
                binding.spinnerWards.setBackground(resources.getDrawable(R.color.white))
            } else {
                var name = binding.edtWarehouseName.text.toString()
                var way = binding.edtWarehouseWay.text.toString()
                if(wards.equals("") || district.equals("") || way.equals("")){
                    dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.error_empty),this)
                }
                else{
                    var adress = binding.edtWarehouseWay.text.toString() + ", "+ wards + ", " + district +", Thành phố Hồ Chí Minh"
                    println("adress: "+adress)
                    var req = WarehouseRes(warehouse.makho,name,adress)
                    updateWarehousePst.constraintCheck(req)
                }
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

    override fun UpdateSucces() {
        dialog.showStartDialog3(getString(R.string.UpdateSucces),this)
        binding.btnSave.setText(getString(R.string.tv_save))

        binding.cartName.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtWarehouseName.setBackground(resources.getDrawable(R.color.gray))
        binding.edtWarehouseName.isEnabled = false

        binding.cartWay.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtWarehouseWay.setBackground(resources.getDrawable(R.color.gray))
        binding.edtWarehouseWay.isEnabled = false

        binding.cartSpinnerDistric.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerDistrict.setBackground(resources.getDrawable(R.color.gray))

        binding.cartSpinnerWards.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerWards.setBackground(resources.getDrawable(R.color.gray))

        val mlist = mutableListOf("")
        mlist.clear()
        setAdapterDistrict(mlist)
        setAdapterWards(mlist)
    }
}