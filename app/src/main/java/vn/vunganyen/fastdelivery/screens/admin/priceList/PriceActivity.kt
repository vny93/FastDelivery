package vn.vunganyen.fastdelivery.screens.admin.priceList

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterDistanceMng
import vn.vunganyen.fastdelivery.data.adapter.AdapterMassMng
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.distance.DistanceRes
import vn.vunganyen.fastdelivery.data.model.distance.DistanceUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.MassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.databinding.ActivityPriceBinding
import vn.vunganyen.fastdelivery.databinding.DialogUpdatePriceBinding

class PriceActivity : AppCompatActivity(),PriceItf {
    lateinit var binding : ActivityPriceBinding
    lateinit var pricePst: PricePst
    lateinit var dialog : Dialog
    lateinit var bindingDialog : DialogUpdatePriceBinding
    var alertDialog: StartAlertDialog = StartAlertDialog()
    var adapterDistance : AdapterDistanceMng = AdapterDistanceMng()
    var adapterMass : AdapterMassMng = AdapterMassMng()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pricePst = PricePst(this)
        dialog = Dialog(this@PriceActivity)
        setToolbar()
        getData()
        invokeUpdateMass()
        invokeUpdateDistance()
    }

    fun getData(){
        pricePst.getListDistance()
    }

    override fun getListDistance(list: List<DistanceRes>) {
        adapterDistance.setData(list)
        binding.rcvDistance.adapter = adapterDistance
        binding.rcvDistance.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun getListMass(list: List<MassRes>) {
        adapterMass.setData(list)
        binding.rcvMass.adapter = adapterMass
        binding.rcvMass.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    fun ShowDialog(gravity: Int, id : Int, check : Int){
        bindingDialog = DialogUpdatePriceBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false)
        } else {
            dialog.setCancelable(false)
        }

        bindingDialog.cancelDialog.setOnClickListener{
            dialog.dismiss()
        }
        bindingDialog.okDialog.setOnClickListener{
            var price = bindingDialog.edtPrice.text.toString().toFloat()
            if(check == 1){
                pricePst.updateMassPrice(MassUPriceReq(price,id))
            }else{
                pricePst.updateDistancePrice(DistanceUPriceReq(price,id))
            }
            dialog.dismiss()
        }
        bindingDialog.dialogUpdatePrice.setOnClickListener{
            bindingDialog.edtPrice.clearFocus()
            bindingDialog.dialogUpdatePrice.hideKeyboard()
        }
        dialog.show()

    }

    fun invokeUpdateMass(){
        adapterMass.clickUpdateMass = {
            id ->  ShowDialog(Gravity.CENTER,id,1)
        }
    }

    fun invokeRemoveMass(){

    }

    fun invokeUpdateDistance(){
        adapterDistance.clickUpdateDistance = {
            id ->  ShowDialog(Gravity.CENTER,id,2)
        }
    }

    fun invokeRemoveDistance(){

    }

    override fun updateMassPrice() {
        alertDialog.showStartDialog3(getString(R.string.update_success),this)
        pricePst.getListDistance()

    }

    override fun updateDistancePrice() {
        alertDialog.showStartDialog3(getString(R.string.update_success),this)
        pricePst.getListDistance()
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
        var toolbar = binding.adToolbarPrice
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}