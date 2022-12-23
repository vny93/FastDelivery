package vn.vunganyen.fastdelivery.screens.staff.parcelMng

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
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
import vn.vunganyen.fastdelivery.data.model.parcel.CancelInforRes
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.StaffGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaReq
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaReq2
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaRes
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusReq
import vn.vunganyen.fastdelivery.data.model.status.GetIdStatusRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetDetailWHReq
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.CheckWayExistReq
import vn.vunganyen.fastdelivery.databinding.DialogBarcodeBinding
import vn.vunganyen.fastdelivery.databinding.DialogCancelInforBinding
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
    lateinit var dialog3: Dialog
    lateinit var dialogBarcode : Dialog
    lateinit var bindingDialog : DialogChooseShipperBinding
    lateinit var bindingDialogCancel: DialogCancelInforBinding
    lateinit var bindingBarcode: DialogBarcodeBinding
    var m_status = ""
    var checkAuto = 0
    companion object{
        var listParcel = ArrayList<StaffGetParcelRes>()
        lateinit var listFilter : ArrayList<StaffGetParcelRes>
    }
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
        callInvokeConfirmSuccess()
        callInvokeReturn()
        callInvokeCancelInfor()

        //new code
        callInvokeAutoGetTheShop()
        callInvokeAutoReturn()
        callInvokeAutoCustomer()
        callInvokeGenerateBarcode()
        dialog2 = context?.let { Dialog(it) }!!
        dialog3 = context?.let { Dialog(it) }!!
        dialogBarcode = context?.let { Dialog(it) }!!
        bindingDialog = DialogChooseShipperBinding.inflate(layoutInflater)
        bindingDialogCancel = DialogCancelInforBinding.inflate(layoutInflater)
        bindingBarcode = DialogBarcodeBinding.inflate(layoutInflater)
        dialog2.setContentView(bindingDialog.root)
        dialog3.setContentView(bindingDialogCancel.root)
        dialogBarcode.setContentView(bindingBarcode.root)
        return binding.root
    }

    fun getData() {
        staffParcelPst.getWarehouseDetail(GetDetailWHReq(idWarehouse))
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
                staffParcelPst.getIdStatus(GetIdStatusReq("Đang lưu kho chờ hoàn trả"), data)
            }
            else if(data.tentrangthai.equals("Hoàn trả thành công")){
                println("xác nhận hoàn trả thành công")
                staffParcelPst.getIdStatus(GetIdStatusReq("Hoàn tất hoàn trả"), data)
            }
        }
    }

    fun callInvokeGetTheShop() {
        adapter.clickGetTheShop = { data ->
            checkAuto = 0
            dataParcel = data
          //  m_status = "Đang lấy hàng"
            m_status = "Chờ lấy hàng"
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
            checkAuto = 0
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

    fun callInvokeConfirmSuccess(){
        adapter.clickOutWarehouse = { data ->
            idShipper = data.mashipper
            if(data.tentrangthai.equals("Đang xuất kho")){
                staffParcelPst.getIdStatus(GetIdStatusReq("Đang giao hàng"), data)
            }
            else if(data.tentrangthai.equals("Đang xuất kho hoàn trả")){
                staffParcelPst.getIdStatus(GetIdStatusReq("Đang hoàn trả"), data)
            }
           // idShipper = data.mashipper
           // staffParcelPst.getIdStatus(GetIdStatusReq("Đang giao hàng"), data)
        }
    }

    fun callInvokeReturn() {
        adapter.clickReturn = { data ->
            checkAuto = 0
            dataParcel = data
            m_status = "Chờ hoàn trả"
            staffParcelPst.getShopDetail(GetShopDetailReq(data.mach))
        }
    }

    fun callInvokeCancelInfor(){
        adapter.clickCancelInfor = { data ->
            staffParcelPst.getCancelInfor(data.mabk)
        }
    }

    //new code
    fun callInvokeAutoGetTheShop(){
        adapter.AutoGetTheShop = { data ->
            checkAuto = 1
            dataParcel = data
            m_status = "Chờ lấy hàng"
            staffParcelPst.getShopDetail(GetShopDetailReq(data.mach))
        }
    }

    fun callInvokeAutoCustomer() {
        adapter.AutoCustomer = { data ->
            checkAuto = 1
            dataParcel = data
            //check coi table đường đi còn kh, nếu còn phải lấy địa chỉ kho đó để tìm ra shipper gần kho đó
            //ngược lại thì lấy địa chỉ người nhận
            staffParcelPst.checkWayExist(CheckWayExistReq(data.mabk, 0), data)
        }
    }

    fun callInvokeAutoReturn(){
        adapter.AutoReturn = { data ->
            checkAuto = 1
            dataParcel = data
            m_status = "Chờ hoàn trả"
            staffParcelPst.getShopDetail(GetShopDetailReq(data.mach))
        }
    }

    fun callInvokeGenerateBarcode(){
        adapter.generateBarcode = { id ->
            staffParcelPst.generateBarcode(id.toString())
        }
    }


    fun setEvent() {
        binding.edtSearchId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var str = binding.edtSearchId.text.toString()
                staffParcelPst.getFilter(str)
            }
        })

        binding.spinnerDistrict.setOnItemClickListener(({ adapterView, view, i, l ->
            adress = adapterView.getItemAtPosition(i).toString()
            if (adress.equals("Tất cả")) {
                adress = ""
                wards = ""
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
            if(adapterView.getItemAtPosition(i).toString().equals("Tất cả")){
                wards = ""
                var req = StGetParcelReq(idWarehouse, adress, status)
                staffParcelPst.filterParcel(req)
            }
            else{
                wards = adapterView.getItemAtPosition(i).toString() + ", " + adress
                println("adress: " + wards)
                var req = StGetParcelReq(idWarehouse, wards, status)
                staffParcelPst.filterParcel(req)
            }
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

        binding.lnlHome.setOnClickListener{
            binding.edtSearchId.clearFocus()
            binding.lnlHome.hideKeyboard()
        }

        binding.refresh.setOnClickListener{
            println("idWarehouse: "+idWarehouse)
            println("adress: "+adress)
            println("wards: "+wards)
            println("status: "+status)
            var s = binding.edtSearchId.text.toString()
            if(wards.equals("")){
                var req = StGetParcelReq(idWarehouse, adress, status)
                if(s.equals("")){
                    staffParcelPst.filterParcel(req)
                }
                else{
                    staffParcelPst.filterParcel2(req,s)
                }
            }
            else{
                var req = StGetParcelReq(idWarehouse, wards, status)
                if(s.equals("")){
                    staffParcelPst.filterParcel(req)
                }
                else{
                    staffParcelPst.filterParcel2(req,s)
                }
            }
       /*     var req = StGetParcelReq(idWarehouse, "", "")
            staffParcelPst.filterParcel(req)

//            var idParcel = binding.edtSearchId.text.toString()
//            staffParcelPst.filterParcel2(req,idParcel)

            binding.spinnerDistrict.setText("",false)
            binding.spinnerStatus.setText("",false)
            binding.spinnerWards.setText("",false)

//            println(binding.edtSearchId.text.toString())
//            staffParcelPst.getFilter(binding.edtSearchId.text.toString())*/
        }
    }

    fun showDialogShipper(gravity : Int, list : List<ShipperAreaRes>, m_status : String, maint_title : String,title : String){
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

        bindingDialog.tvTitleMain.setText(maint_title)
        bindingDialog.tvTitle.setText(title)
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

    fun showDialogCancelInfor(gravity : Int, res : CancelInforRes){
        val window = dialog3.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.CENTER == gravity) {
            dialog3.setCancelable(true)
        } else {
            dialog3.setCancelable(true)
        }

        bindingDialogCancel.tvStatus.setText(res.tentrangthai)
        bindingDialogCancel.tvId.setText(res.mashipper)
        bindingDialogCancel.tvName.setText(res.hoten)
        bindingDialogCancel.tvPhone.setText(res.sdt)
        dialog3.show()
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

    override fun getWarehouseDetail(req: WarehouseRes) {
        binding.tvWarehouseName.setText(req.tenkho)
    }

    override fun getIdStatus(res: GetIdStatusRes, req2: StaffGetParcelRes) {
        println("mã trạng thái: " + res.matrangthai)
        staffParcelPst.addDetailStatus(
            DetailStatusReq(req2.mabk, res.matrangthai, SplashActivity.profile.result.manv, idShipper)
        )
        idShipper =""
    }

    override fun addDetailStatus() {
        println("idWarehouse2: "+idWarehouse)
        println("adress2: "+adress)
        println("wards2: "+wards)
        println("status2: "+status)
        var s = binding.edtSearchId.text.toString()
        println("s2: "+s)
        if(wards.equals("")){
            var req = StGetParcelReq(idWarehouse, adress, status)
            if(s.equals("")){
                staffParcelPst.filterParcel(req)
            }
            else{
                staffParcelPst.filterParcel2(req,s)
            }
        }
        else{
            var req = StGetParcelReq(idWarehouse, wards, status)
            if(s.equals("")){
                staffParcelPst.filterParcel(req)
            }
            else{
                staffParcelPst.filterParcel2(req,s)
            }
            //   staffParcelPst.filterParcel(req)
            //   staffParcelPst.filterParcel2(req,s)
        }

     /*   var req = StGetParcelReq(idWarehouse, adress, status)
        staffParcelPst.filterParcel(req)
      */
//        var req = StGetParcelReq(idWarehouse, "", "")
//        //    staffParcelPst.filterParcel(req)
//        var idParcel = binding.edtSearchId.text.toString()
//        staffParcelPst.filterParcel2(req,idParcel)
        idShipper = ""
    }

    override fun getShopDetail(res: GetShopDetailRes) {
        println("checkAuto: "+checkAuto)
        if(checkAuto == 0){
            if(m_status.equals("Chờ lấy hàng")){
                var adress = getStrAdress(res.diachi)
                var status = "Đang lấy hàng"
                println(adress)
                staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
            }
            else if(m_status.equals("Chờ hoàn trả")){
                var adress = getStrAdress(res.diachi)
                var status = "Đang giao hàng"
                println(adress)
                staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
            }
        }
        else{
            if(m_status.equals("Chờ lấy hàng")){
                var adress = getStrAdress(res.diachi)
                var statusGetListCancel = "Từ chối lấy hàng"
                println(adress)
                staffParcelPst.getShipperArea2(dataParcel.mabk,statusGetListCancel, ShipperAreaReq2(adress))
            }
            else if(m_status.equals("Chờ hoàn trả")){
                var adress = getStrAdress(res.diachi)
                var statusGetListCancel = "Từ chối hoàn trả"
                println(adress)
                staffParcelPst.getShipperArea2(dataParcel.mabk,statusGetListCancel, ShipperAreaReq2(adress))
            }
        }


    }

    override fun wayExist(res: WarehouseRes, data: StaffGetParcelRes) {
        if(checkAuto == 0){
            //truyền địa chỉ kho vào nếu còn phải đi qua kho tiếp theo
            var adress = getStrAdress(res.diachi)
            // m_status = "Đang xuất kho"
            m_status = "Chờ xuất kho" //để cập nhật trạng thái của bưu kiện
            var status = "Đang giao hàng" //lấy ra shipper và số lượng bk shipper đang giao hàng
            println(adress)
            staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
        }
        else{
            var adress = getStrAdress(res.diachi)
            m_status = "Chờ xuất kho" //để cập nhật trạng thái của bưu kiện
            var statusGetListCancel = "Từ chối giao hàng"
            println(adress)
            staffParcelPst.getShipperArea2(dataParcel.mabk,statusGetListCancel, ShipperAreaReq2(adress))
        }
    }

    override fun wayNotExist(data: StaffGetParcelRes) {
        if(checkAuto == 0){
            //truyền địa chỉ người nhận vào nếu hết kho và giao luôn cho khách hàng
            var adress = getStrAdress(data.diachinguoinhan)
            //    m_status = "Đang xuất kho"
            m_status = "Chờ xuất kho" //để cập nhật trạng thái của bưu kiện
            var status = "Đang giao hàng" //lấy ra shipper và số lượng bk shipper đang giao hàng
            println(adress)
            staffParcelPst.getShipperArea(ShipperAreaReq(adress,status))
        }
        else{
            var adress = getStrAdress(data.diachinguoinhan)
            m_status = "Chờ xuất kho" //để cập nhật trạng thái của bưu kiện
            var statusGetListCancel = "Từ chối giao hàng"
            println(adress)
            staffParcelPst.getShipperArea2(dataParcel.mabk,statusGetListCancel, ShipperAreaReq2(adress))
        }
    }

    override fun count(res: CountRes,data : StaffGetParcelRes) {
        if(res.count <= 2){
            staffParcelPst.getIdStatus(GetIdStatusReq("Đang lưu kho"), data)
        }
        else{
            staffParcelPst.getIdStatus(GetIdStatusReq("Đang lưu kho chờ hoàn trả"), data)
        }
    }

    override fun cancelInfor(res: CancelInforRes) {
        showDialogCancelInfor(Gravity.CENTER,res)
    }

    override fun enoughOrder() {
        context?.let { it1 -> dialog.showStartDialog3(getString(R.string.tv_enoughOrder), it1)}
    }

    override fun autoShipper(res: ShipperAreaRes) {
        idShipper = res.manv
        context?.let { it1 -> dialog.showStartDialog3(getString(R.string.tv_autoShipperSuccess,res.manv,res.hoten), it1)}
        staffParcelPst.getIdStatus(GetIdStatusReq(m_status), dataParcel)
    }

    override fun notFindShipper() {
        context?.let { it1 -> dialog.showStartDialog3(getString(R.string.tv_notFindShipper), it1)}
    }

    override fun bitmap(bitmap: Bitmap) {
        showDialogBarcode(Gravity.CENTER,bitmap)
    }

    fun showDialogBarcode(gravity : Int, bitmap: Bitmap){
        val window = dialogBarcode.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.CENTER == gravity) {
            dialogBarcode.setCancelable(true)
        } else {
            dialogBarcode.setCancelable(true)
        }

        bindingBarcode.barcodeImv.setImageBitmap(bitmap)
        var manager : InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
       // manager.hideSoftInputFromWindow(binding.edtText.applicationWindowToken,0)
        dialogBarcode.show()
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

    override fun getShipperArea(list: List<ShipperAreaRes>,adress : String, status : String) {
        var main_title = ""
        var title = ""
        if(status.equals("Đang lấy hàng")){
            main_title = "Lấy hàng Khu vực: "+adress
            title = "Số lượng bưu kiện đang giao lấy"
        }
        else {
            main_title = "Giao hàng Khu vực: "+adress
            title = "Số lượng bưu kiện đang giao"
        }
        showDialogShipper(Gravity.CENTER, list, m_status,main_title,title)
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

    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }
}