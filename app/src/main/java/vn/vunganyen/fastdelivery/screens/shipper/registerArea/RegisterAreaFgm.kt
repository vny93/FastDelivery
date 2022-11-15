package vn.vunganyen.fastdelivery.screens.shipper.registerArea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterAreaMng
import vn.vunganyen.fastdelivery.data.model.area.GetListAreaRes
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.detailRegister.GetAreaShipperReq
import vn.vunganyen.fastdelivery.data.model.detailRegister.GetAreaShipperRes
import vn.vunganyen.fastdelivery.data.model.detailRegister.InsertAreaReq
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelReq
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.databinding.FragmentRegisterAreaFgmBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity

class RegisterAreaFgm : Fragment(), RegisterAreaItf {
    lateinit var binding : FragmentRegisterAreaFgmBinding
    lateinit var registerAreaPst: RegisterAreaPst
    var adapter : AdapterAreaMng = AdapterAreaMng()
    var dialog : StartAlertDialog = StartAlertDialog()
    var idShipper = ""
    var idArea = 0
    var listArea = ArrayList<GetListAreaRes>()
    var listAreaName = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterAreaFgmBinding.inflate(layoutInflater)
        registerAreaPst = RegisterAreaPst(this)
        idShipper = SplashActivity.profile.result.manv
        getData()
        setEvent()
        callInvokeDeleteArea()
        return binding.root
    }

    fun getData(){
        registerAreaPst.getListRegisterArea(GetAreaShipperReq(idShipper))
        registerAreaPst.getListArea()
    }

    fun setEvent(){
        binding.spinnerArea.setOnItemClickListener(({ adapterView, view, i, l ->
            for (list in listArea) {
                if (list.tenkhuvuc.equals(adapterView.getItemAtPosition(i).toString())) {
                    println("makhuvuc:" + list.makhuvuc)
                    idArea = list.makhuvuc
                }
            }
        }))
        binding.btnSave.setOnClickListener{
            var req = InsertAreaReq(idShipper,idArea)
            registerAreaPst.checkEmpty(req)
        }
    }

    fun callInvokeDeleteArea(){
        adapter.clickRemoveArea = {
            id -> context?.let { it1 -> dialog.showStartDialog4(getString(R.string.mess_delete_area), it1) }
            dialog.clickOk = { ->
                var req = InsertAreaReq(idShipper,id)
                registerAreaPst.deleteArea(req)
            }
        }
    }

    override fun getListRegisterArea(list: List<GetListAreaRes>) {
        adapter.setData(list)
        binding.rcvListArea.adapter = adapter
        binding.rcvListArea.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun getListArea(list: List<GetListAreaRes>) {
        listArea = list as ArrayList<GetListAreaRes>
        for (i in 0..list.size - 1) {
            listAreaName.add(list.get(i).tenkhuvuc)
        }
        setAdapterAreat(listAreaName)
    }

    override fun addSuccess() {
        context?.let { it1 -> dialog.showStartDialog3(getString(R.string.mess_register_area), it1) }
        binding.spinnerArea.setText("",false)
        getData()
    }

    override fun registerExist() {
        context?.let { it1 -> dialog.showStartDialog3(getString(R.string.mess_register_area_error), it1) }
    }

    override fun Empty() {
        context?.let { it1 -> dialog.showStartDialog3(getString(R.string.mess_register_area_empty), it1) }
    }

    override fun deleteSuccess() {
        context?.let { it1 -> dialog.showStartDialog3(getString(R.string.RemoveStaffSuccess), it1) }
        getData()
    }

    fun setAdapterAreat(list: List<String>) {
        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        binding.spinnerArea.setAdapter(adapter)
    }

}