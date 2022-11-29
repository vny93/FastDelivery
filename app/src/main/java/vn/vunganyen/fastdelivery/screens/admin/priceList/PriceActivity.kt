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
import vn.vunganyen.fastdelivery.data.model.distance.AddDistanceReq
import vn.vunganyen.fastdelivery.data.model.distance.DeleteDistanceReq
import vn.vunganyen.fastdelivery.data.model.distance.DistanceRes
import vn.vunganyen.fastdelivery.data.model.distance.DistanceUPriceReq
import vn.vunganyen.fastdelivery.data.model.mass.AddMassReq
import vn.vunganyen.fastdelivery.data.model.mass.DeleteMassReq
import vn.vunganyen.fastdelivery.data.model.mass.MassRes
import vn.vunganyen.fastdelivery.data.model.mass.MassUPriceReq
import vn.vunganyen.fastdelivery.databinding.ActivityPriceBinding
import vn.vunganyen.fastdelivery.databinding.DialogAddDistanceBinding
import vn.vunganyen.fastdelivery.databinding.DialogAddMassBinding
import vn.vunganyen.fastdelivery.databinding.DialogUpdatePriceBinding

class PriceActivity : AppCompatActivity(),PriceItf {
    lateinit var binding : ActivityPriceBinding
    lateinit var pricePst: PricePst
    lateinit var dialog : Dialog
    lateinit var bindingDialog : DialogUpdatePriceBinding
    lateinit var binddingDialogDistance : DialogAddDistanceBinding
    lateinit var bindingDialogMass : DialogAddMassBinding
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
        setEvent()
        invokeUpdateMass()
        invokeUpdateDistance()
        invokeRemoveMass()
        invokeRemoveDistance()
    }

    fun getData(){
        pricePst.getListDistance()
        pricePst.getListMass()
    }

    fun setEvent(){
        binding.imvAddMass.setOnClickListener{
            ShowDialogMass(Gravity.CENTER,)
        }

        binding.imvAddDistance.setOnClickListener{
            ShowDialogDistance(Gravity.CENTER)
        }
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
            var price = 0F
            if(bindingDialog.edtPrice.text.toString().isEmpty()){
                alertDialog.showStartDialog3(getString(R.string.empty_price),this)
                return@setOnClickListener
            }
            price = bindingDialog.edtPrice.text.toString().toFloat()
            if(price.toInt() == 0){
                alertDialog.showStartDialog3(getString(R.string.priceError),this)
                return@setOnClickListener
            }
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

    fun ShowDialogDistance(gravity: Int){
        binddingDialogDistance = DialogAddDistanceBinding.inflate(layoutInflater)
        dialog.setContentView(binddingDialogDistance.root)
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

        binddingDialogDistance.btnCancel.setOnClickListener{
            dialog.dismiss()
        }
        binddingDialogDistance.btnAccept.setOnClickListener{
            var distanceStart = binddingDialogDistance.edtDistantStart.text.toString()
            var distanceEnd = binddingDialogDistance.edtDistanceEnd.text.toString()
            var price = binddingDialogDistance.edtDistancePrice.text.toString()
            if(distanceStart.isEmpty() || distanceEnd.isEmpty() || price.isEmpty()){
                alertDialog.showStartDialog3(getString(R.string.error_empty),this)
            }
            else{
                pricePst.checkMaxDistance(AddDistanceReq(distanceStart.toInt(),distanceEnd.toInt(),price.toFloat()))
               // dialog.dismiss()
            }
        }
        binddingDialogDistance.dialogDistance.setOnClickListener{
            binddingDialogDistance.edtDistantStart.clearFocus()
            binddingDialogDistance.edtDistanceEnd.clearFocus()
            binddingDialogDistance.edtDistancePrice.clearFocus()
            binddingDialogDistance.dialogDistance.hideKeyboard()
        }
        dialog.show()

    }

    fun ShowDialogMass(gravity: Int){
        bindingDialogMass = DialogAddMassBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialogMass.root)
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

        bindingDialogMass.btnCancel.setOnClickListener{
            dialog.dismiss()
        }
        bindingDialogMass.btnAccept.setOnClickListener{
            var massStart = bindingDialogMass.edtMassStart.text.toString()
            var massEnd = bindingDialogMass.edtMassEnd.text.toString()
            var price = bindingDialogMass.edtMassPrice.text.toString()
            if(massStart.isEmpty() || massEnd.isEmpty() || price.isEmpty()){
                alertDialog.showStartDialog3(getString(R.string.error_empty),this)
            }
            else{
                pricePst.checkMaxMass(AddMassReq(massStart.toInt(),massEnd.toInt(),price.toFloat()))
//                dialog.dismiss()
            }
        }
        bindingDialogMass.dialogMass.setOnClickListener{
            bindingDialogMass.edtMassStart.clearFocus()
            bindingDialogMass.edtMassEnd.clearFocus()
            bindingDialogMass.edtMassPrice.clearFocus()
            bindingDialogMass.dialogMass.hideKeyboard()
        }
        dialog.show()

    }

    fun invokeUpdateMass(){
        adapterMass.clickUpdateMass = {
            id ->  ShowDialog(Gravity.CENTER,id,1)
        }
    }

    fun invokeRemoveMass(){
        adapterMass.clickRemoveMass = {
            id ->
            alertDialog.showStartDialog4(getString(R.string.RemoveMass),this)
            alertDialog.clickOk = {
                ->pricePst.deleteMass(DeleteMassReq(id))
            }
        }
    }

    fun invokeUpdateDistance(){
        adapterDistance.clickUpdateDistance = {
            id ->  ShowDialog(Gravity.CENTER,id,2)
        }
    }

    fun invokeRemoveDistance(){
        adapterDistance.clickRemoveDistance = {
            id ->
            alertDialog.showStartDialog4(getString(R.string.RemoveDistance),this)
            alertDialog.clickOk = {
                ->pricePst.deleteDistance(DeleteDistanceReq(id))
            }
        }
    }

    override fun updateMassPrice() {
        alertDialog.showStartDialog3(getString(R.string.update_success),this)
        pricePst.getListMass()

    }

    override fun updateDistancePrice() {
        alertDialog.showStartDialog3(getString(R.string.update_success),this)
        pricePst.getListDistance()
    }

    override fun deleteMass() {
        alertDialog.showStartDialog3(getString(R.string.RemoveMassSuccess),this)
        getData()
    }

    override fun deleteDistance() {
        alertDialog.showStartDialog3(getString(R.string.RemoveDistanceSuccess),this)
        getData()
    }

    override fun massError(max : Int) {
        alertDialog.showStartDialog3(getString(R.string.massErorr,max),this)
    }

    override fun distanceErorr(max : Int) {
        alertDialog.showStartDialog3(getString(R.string.distanceErorr,max),this)
    }

    override fun addDistance() {
        dialog.dismiss()
        alertDialog.showStartDialog3(getString(R.string.AddDistanceSuccess),this)
        getData()
    }

    override fun addMass() {
        dialog.dismiss()
        alertDialog.showStartDialog3(getString(R.string.AddMassSuccess),this)
        getData()
    }

    override fun comtopareMassError() {
        alertDialog.showStartDialog3(getString(R.string.comtopareMassError),this)
    }

    override fun comtopareDistanceError() {
        alertDialog.showStartDialog3(getString(R.string.comtopareDistanceError),this)
    }

    override fun priceError() {
        alertDialog.showStartDialog3(getString(R.string.priceError),this)
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