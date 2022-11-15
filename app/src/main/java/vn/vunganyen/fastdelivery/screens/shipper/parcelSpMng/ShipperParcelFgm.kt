package vn.vunganyen.fastdelivery.screens.shipper.parcelSpMng

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterShipperParcelMng
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusReq
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelRes
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.way.CheckWayExistReq
import vn.vunganyen.fastdelivery.databinding.DialogChooseShipperBinding
import vn.vunganyen.fastdelivery.databinding.DialogUpdateStatusPcBinding
import vn.vunganyen.fastdelivery.databinding.FragmentShipperParcelFgmBinding
import vn.vunganyen.fastdelivery.databinding.FragmentStaffParceBinding
import vn.vunganyen.fastdelivery.screens.login.LoginActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity


class ShipperParcelFgm : Fragment(), ShipperParcelItf {
    lateinit var binding : FragmentShipperParcelFgmBinding
    lateinit var shipperParcelPst: ShipperParcelPst
    var adapter : AdapterShipperParcelMng  = AdapterShipperParcelMng()
    var dialog : StartAlertDialog = StartAlertDialog()
    var idStaff = ""
    var idShipper = ""
    var status = ""
    var adress = ""
    var wards = ""
    var listStatus = ArrayList<String>()
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()
    lateinit var dialog2: Dialog
    lateinit var bindingDialog : DialogUpdateStatusPcBinding
    var list = ArrayList<String>()
    var updateNameStatus = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShipperParcelFgmBinding.inflate(layoutInflater)
        shipperParcelPst = ShipperParcelPst(this)
        idShipper = SplashActivity.profile.result.manv

        dialog2 = context?.let { Dialog(it) }!!
        bindingDialog = DialogUpdateStatusPcBinding.inflate(layoutInflater)
        dialog2.setContentView(bindingDialog.root)

        getData()
        setEvent()
//        callInvokeGetShopDone()
//        callInvokeCancelPickParcel()
        callInvokkeDelivering()

        //new
        callInvokeGetShop()
        callInvokeDelivering()
        callInvokeOutWarehouse()
        return binding.root
    }

    fun getData(){
        shipperParcelPst.getListStatus()
        shipperParcelPst.getDistrict()
        var req = SpGetParcelReq(adress, status,idShipper)
        shipperParcelPst.filterParcel(req)
    }

//    fun callInvokeGetShopDone(){
//        adapter.clickGetShopDone = {
//                data -> shipperParcelPst.getIdStatus(GetIdStatusReq("Lấy hàng thành công"), data)
//        }
//    }


//    fun callInvokeCancelPickParcel(){
//        adapter.clickCancelPickParcel = { data ->
//            context?.let { it1 -> dialog.showStartDialog4(getString(R.string.mess_cancel_parcel), it1) }
//            dialog.clickOk = { ->
//                 shipperParcelPst.getIdStatus(GetIdStatusReq("Từ chối lấy hàng"), data)
//            }
//        }
//    }

    fun callInvokkeDelivering(){
        adapter.clickDelivering = {
                data ->
            list.clear()
            list.add("Đang giao hàng")
            showDialogShipper(Gravity.CENTER,list,data)
           // shipperParcelPst.getIdStatus(GetIdStatusReq("Đang giao hàng"),data)
        }
    }

    fun callInvokeGetShop(){
        adapter.clickGetShop = {
            data ->
            list.clear()
            list.add("Lấy hàng thành công")
            list.add("Từ chối lấy hàng")
            showDialogShipper(Gravity.CENTER,list,data)
        }
    }

    fun callInvokeDelivering(){
        adapter.clickDelived = { data ->
            if(data.htvanchuyen.equals("Giao hàng trong 2h")){
                list.clear()
                list.add("Giao hàng thành công")
                list.add("Giao hàng thất bại")
                list.add("Khách hàng hủy")
                showDialogShipper(Gravity.CENTER,list,data)
            }
            else{
                shipperParcelPst.checkWayExist(CheckWayExistReq(data.mabk),data)
            }

        }
    }

    fun callInvokeOutWarehouse(){
        adapter.clickOutWarehouse = {
            data ->
            list.clear()
            list.add("Đang giao hàng")
            list.add("Từ chối giao hàng")
            showDialogShipper(Gravity.CENTER,list,data)
        }
    }

    fun setEvent(){
        binding.spinnerDistrict.setOnItemClickListener(({ adapterView, view, i, l ->
            adress = adapterView.getItemAtPosition(i).toString()
            if (adress.equals("Tất cả")) {
                adress = ""
                val mlist = mutableListOf("")
                mlist.clear()
                setAdapterWards(mlist)
                binding.spinnerWards.setText("")
            } else {
                for (list in listDistrict) {
                    if (list.name.equals(adapterView.getItemAtPosition(i).toString())) {
                        println("code:" + list.code)
                        shipperParcelPst.getWards(list.code)
                    }
                }
            }
            println("adress: " + adress)
            var req = SpGetParcelReq(adress, status,idShipper)
            shipperParcelPst.filterParcel(req)
        }))

        binding.spinnerWards.setOnItemClickListener(({ adapterView, view, i, l ->
            wards = adapterView.getItemAtPosition(i).toString() + ", " + adress
            println("adress: " + wards)
            var req = SpGetParcelReq(wards, status, idShipper)
            shipperParcelPst.filterParcel(req)
        }))

        binding.spinnerStatus.setOnItemClickListener(({ adapterView, view, i, l ->
            status = adapterView.getItemAtPosition(i).toString()
            if (status.equals("Tất cả")) {
                status = ""
            }
            println("status: " + status)
            var req = SpGetParcelReq(adress, status,idShipper)
            shipperParcelPst.filterParcel(req)
        }))

        binding.refresh.setOnClickListener{
            var req = SpGetParcelReq("", "",idShipper)
            shipperParcelPst.filterParcel(req)
            binding.spinnerDistrict.setText("",false)
            binding.spinnerStatus.setText("",false)
            binding.spinnerWards.setText("",false)
        }

        bindingDialog.dialogSpinnerStatus.setOnItemClickListener { parent, view, position, id ->
            updateNameStatus =  parent.getItemAtPosition(position).toString()
            println("updateNameStatus "+updateNameStatus)
        }
    }

    fun showDialogShipper(gravity : Int, list : List<String>, data : StGetParcelRes){
        val window = dialog2.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.CENTER == gravity) {
            dialog2.setCancelable(false)
        } else {
            dialog2.setCancelable(false)
        }

        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        bindingDialog.dialogSpinnerStatus.setAdapter(adapter)


        bindingDialog.okDialog.setOnClickListener{
            if(updateNameStatus.isEmpty()){
                context?.let { it1 ->
                    dialog.showStartDialog3(getString(R.string.error_empty_status), it1
                    )
                }
            }
            else{
                shipperParcelPst.getIdStatus(GetIdStatusReq(updateNameStatus),data)
                updateNameStatus = ""
                bindingDialog.dialogSpinnerStatus.setText("",false)
                dialog2.dismiss()
            }
        }

        bindingDialog.cancelDialog.setOnClickListener{
            updateNameStatus = ""
            bindingDialog.dialogSpinnerStatus.setText("",false)
            dialog2.dismiss()
        }

        dialog2.show()
    }

    override fun getListStatus(list: List<ListStatusRes>) {
        listStatus.add("Tất cả")
        for (i in 0..list.size - 1) {
            listStatus.add(list.get(i).tentrangthai)
        }
        setAdapterStatus(listStatus)
    }

    fun setAdapterStatus(list: List<String>) {
        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        binding.spinnerStatus.setAdapter(adapter)
        binding.spinnerStatus.setHint("Tất cả")
    }

    override fun getListDistrict(list: List<DistrictRes>) {
        listDistrict = list as ArrayList<DistrictRes>
        listDistrictName.add("Tất cả")
        for (i in 0..list.size - 1) {
            listDistrictName.add(list.get(i).name)
        }
        setAdapterDistrict(listDistrictName)
    }

    fun setAdapterDistrict(list: List<String>) {
        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        binding.spinnerDistrict.setAdapter(adapter)
        binding.spinnerDistrict.setHint("Tất cả")
        binding.spinnerWards.setHint("Tất cả")
    }

    override fun getListWards(list: List<WardsRes>) {
        listWardstName.add("Tất cả")
        for (i in 0..list.size - 1) {
            listWardstName.add(list.get(i).name)
        }
        setAdapterWards(listWardstName)
    }

    fun setAdapterWards(list: List<String>) {
        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        binding.spinnerWards.setAdapter(adapter)
        binding.spinnerWards.setHint("Tất cả")
    }


    override fun getListParcel(list: List<StGetParcelRes>) {
        adapter.setData(list)
        binding.rcvListParcel.adapter = adapter
        binding.rcvListParcel.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
    }

    override fun getIdStatus(res: GetIdStatusRes, req2: StGetParcelRes) {
        println("mã trạng thái: " + res.matrangthai)
        shipperParcelPst.addDetailStatus(
            DetailStatusReq(req2.mabk, res.matrangthai, idStaff, idShipper)
        )
    }

    override fun addDetailStatus() {
        var req = SpGetParcelReq(adress, status,idShipper)
        shipperParcelPst.filterParcel(req)
    }

    override fun checkWayExist(res: Boolean, data: StGetParcelRes) {
        list.clear()
        if(res == true){
            list.add("Đã chuyễn kho")
        }
        else{
            list.add("Giao hàng thành công")
            list.add("Giao hàng thất bại")
            list.add("Khách hàng hủy")
        }
        showDialogShipper(Gravity.CENTER,list,data)
    }

}