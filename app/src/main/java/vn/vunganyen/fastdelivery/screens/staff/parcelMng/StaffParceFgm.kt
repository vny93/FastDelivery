package vn.vunganyen.fastdelivery.screens.staff.parcelMng

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterShipperArea
import vn.vunganyen.fastdelivery.data.adapter.AdapterStaffParcelMng
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.detailStatus.CountRes
import vn.vunganyen.fastdelivery.data.model.detailStatus.DetailStatusReq
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.StaffGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaReq
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.CheckWayExistReq
import vn.vunganyen.fastdelivery.databinding.DialogChooseShipperBinding
import vn.vunganyen.fastdelivery.databinding.FragmentStaffParceBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity


class StaffParceFgm : Fragment(), StaffParcelItf {
    lateinit var binding: FragmentStaffParceBinding
    lateinit var staffParcelPst: StaffParcelPst
    var dialog: StartAlertDialog = StartAlertDialog()
    var adapter: AdapterStaffParcelMng = AdapterStaffParcelMng()
    var adapterShipper: AdapterShipperArea = AdapterShipperArea()
    var idWarehouse = 0
    var idShipper = ""
    var status = ""
    var adress = ""
    var wards = ""
    lateinit var dataParcel : StaffGetParcelRes
    var listStatus = ArrayList<String>()
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()
    lateinit var dialog2: Dialog
    lateinit var bindingDialog : DialogChooseShipperBinding
    var m_status = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStaffParceBinding.inflate(layoutInflater)
        staffParcelPst = StaffParcelPst(this)

        idWarehouse = SplashActivity.profile.result.makho
        binding.tvHomeName.setText(SplashActivity.profile.result.hoten)
        getData()
        setEvent()
        callInvokeConfirm()
        callInvokeGetTheShop()
        callInvokeSaveWarehouse()

        callInvokeRadio()
        callInvokeGetCustomer()
        dialog2 = context?.let { Dialog(it) }!!
        bindingDialog = DialogChooseShipperBinding.inflate(layoutInflater)
        dialog2.setContentView(bindingDialog.root)
        return binding.root
    }

    fun getData() {
        staffParcelPst.getListStatus()
        staffParcelPst.getDistrict()
        var req = StGetParcelReq(idWarehouse, adress, status)
        staffParcelPst.filterParcel(req)
    }

    fun callInvokeConfirm() {
        adapter.clickConfirm = { data ->
            if(data.tentrangthai.equals("Chờ xác nhận")){
                staffParcelPst.getIdStatus(GetIdStatusReq("Đã xác nhận"), data)
            }
            else if(data.tentrangthai.equals("Giao hàng thành công")){
                println("xác nhận giao thành công")
                staffParcelPst.getIdStatus(GetIdStatusReq("Hoàn tất"), data)
            }
            else if(data.tentrangthai.equals("Giao hàng thất bại")){
                //check số lần trước nếu quá 3 lần -> hoàn trả luôn
                println("xác nhận giao thất bại")
                staffParcelPst.countDeliveredFail(data)
            }
            else if(data.tentrangthai.equals("Khách hàng hủy")){
                println("xác nhận hoàn trả")
                staffParcelPst.getIdStatus(GetIdStatusReq("Hoàn trả"), data)
            }

        }
    }

    fun callInvokeGetTheShop() {
        adapter.clickGetTheShop = { data ->
            dataParcel = data
            m_status = "Đang lấy hàng"
            staffParcelPst.getShopDetail(GetShopDetailReq(data.mach))
        }
    }

    fun callInvokeSaveWarehouse() {
        adapter.clickSaveWarehouse = { data ->
            staffParcelPst.getIdStatus(GetIdStatusReq("Đang lưu kho"), data)
        }
    }

    fun callInvokeRadio(){
        adapterShipper.clickRadio = {
                data -> idShipper = data.manv
        }
    }

    fun callInvokeGetCustomer() {
        adapter.clickGetCustomer = { data ->
            dataParcel = data
        //    m_status = "Đang xuất kho"

            //check coi table đường đi còn kh, nếu còn phải lấy địa chỉ kho đó để tìm ra shipper gần kho đó
            //ngược lại thì lấy địa chỉ người nhận
            staffParcelPst.checkWayExist(CheckWayExistReq(data.mabk,0),data)
//            var str = data.diachinguoinhan.trim()
//            val arrWord = str.split(", ")
//            var count = 0
//            var adress = ""
//            for (word in arrWord) {
//                println(word)
//                if(count == 2){
//                    adress = word
//                }
//                count++
//            }
//            var status = "Đang giao hàng"
//            println(adress)
//            staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
        }
    }

//    fun callInvokeConfirmSuccess(){
//        adapter.clickConfirmSucess = { data ->
//            //xác nhận -> cập nhật trạng thái hoàn tất
//        }
//    }
//
//    fun callInvokeConfrimFail(){
//        adapter.clickConfirmFail = { data ->
//            //xác nhận -> cập nhật trang thái lưu kho, nếu quá 3 lần thất bại thì tự thành hoàn trả
//        }
//    }
//
//    fun callInvokeReturn(){
//        adapter.clickReturn = { data ->
//            //xác nhận -> thì tự cập nhật thành hoàn trà
//        }
//    }


    fun setEvent() {
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
                        staffParcelPst.getWards(list.code)
                    }
                }
            }
            println("adress: " + adress)
            var req = StGetParcelReq(idWarehouse, adress, status)
            staffParcelPst.filterParcel(req)
        }))

        binding.spinnerWards.setOnItemClickListener(({ adapterView, view, i, l ->
            wards = adapterView.getItemAtPosition(i).toString() + ", " + adress
            println("adress: " + wards)
            var req = StGetParcelReq(idWarehouse, wards, status)
            staffParcelPst.filterParcel(req)
        }))

        binding.spinnerStatus.setOnItemClickListener(({ adapterView, view, i, l ->
            status = adapterView.getItemAtPosition(i).toString()
            if (status.equals("Tất cả")) {
                status = ""
            }
            println("status: " + status)
            var req = StGetParcelReq(idWarehouse, adress, status)
            staffParcelPst.filterParcel(req)
        }))

        binding.refresh.setOnClickListener{
            var req = StGetParcelReq(idWarehouse, "", "")
            staffParcelPst.filterParcel(req)
            binding.spinnerDistrict.setText("",false)
            binding.spinnerStatus.setText("",false)
            binding.spinnerWards.setText("",false)
        }
    }

    fun showDialogShipper(gravity : Int, list : List<ShipperAreaRes>, m_status : String){
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

        adapterShipper.setData(list)
        bindingDialog.rcvListShipper.adapter = adapterShipper
        bindingDialog.rcvListShipper.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        bindingDialog.btnAccept.setOnClickListener{
            if(idShipper.isEmpty()){
                context?.let { it1 ->
                    dialog.showStartDialog3(getString(R.string.error_empty_shipper), it1
                    )
                }
            }
            else{
                staffParcelPst.getIdStatus(GetIdStatusReq(m_status), dataParcel)
                adapterShipper.selectedPosition = -1
                dialog2.dismiss()
            }
        }

        bindingDialog.btnCancel.setOnClickListener{
            adapterShipper.selectedPosition = -1
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

    override fun getIdStatus(res: GetIdStatusRes, req2: StaffGetParcelRes) {
        println("mã trạng thái: " + res.matrangthai)
        staffParcelPst.addDetailStatus(
            DetailStatusReq(req2.mabk, res.matrangthai, SplashActivity.profile.result.manv, idShipper)
        )
    }

    override fun addDetailStatus() {
        var req = StGetParcelReq(idWarehouse, adress, status)
        staffParcelPst.filterParcel(req)
        idShipper = ""
    }

    override fun getShopDetail(res: GetShopDetailRes) {
        var adress = getStrAdress(res.diachi)
        var status = "Đang lấy hàng"
        println(adress)
        staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
    }

    override fun wayExist(res: WarehouseRes, data: StaffGetParcelRes) {
        //truyền địa chỉ kho vào nếu còn phải đi qua kho tiếp theo
        var adress = getStrAdress(res.diachi)
        m_status = "Đang xuất kho"
        var status = "Đang giao hàng" //lấy ra shipper và số lượng bk shipper đang giao hàng
        println(adress)
        staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
    }

    override fun wayNotExist(data: StaffGetParcelRes) {
        //truyền địa chỉ người nhận vào nếu hết kho và giao luôn cho khách hàng
        var adress = getStrAdress(data.diachinguoinhan)
        m_status = "Đang xuất kho"
        var status = "Đang giao hàng" //lấy ra shipper và số lượng bk shipper đang giao hàng
        println(adress)
        staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
    }

    override fun count(res: CountRes,data : StaffGetParcelRes) {
        if(res.count <= 2){
            staffParcelPst.getIdStatus(GetIdStatusReq("Đang lưu kho"), data)
        }
        else{
            staffParcelPst.getIdStatus(GetIdStatusReq("Hoàn trả"), data)
        }
    }

    fun getStrAdress(mstr : String): String{
        var str = mstr.trim()
        val arrWord = str.split(", ")
        var count = 0
        var adress = ""
        for (word in arrWord) {
            println(word)
            if(count == 2){
                adress = word
            }
            count++
        }
        return adress
    }

    override fun getShipperArea(list: List<ShipperAreaRes>) {
        showDialogShipper(Gravity.CENTER, list, m_status)
    }

    fun setAdapterWards(list: List<String>) {
        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, list) }
        binding.spinnerWards.setAdapter(adapter)
        binding.spinnerWards.setHint("Tất cả")
    }


    override fun getListParcel(list: List<StaffGetParcelRes>) {
        adapter.setData(list)
        binding.rcvListParcel.adapter = adapter
        binding.rcvListParcel.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
    }
}