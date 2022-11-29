package vn.vunganyen.fastdelivery.screens.admin.parcelMng.assignment

import android.app.Dialog
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import demo.kotlin.model.dijkstra.Edge
import demo.kotlin.model.dijkstra.Vert
import vn.vunganyen.fastdelivery.data.adapter.AdapterAdminParcelMng
import vn.vunganyen.fastdelivery.data.adapter.AdapterParcelWh
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.StaffGetParcelRes
import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhReq
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.WayReq
import vn.vunganyen.fastdelivery.databinding.ActivityAssignmentMngBinding
import vn.vunganyen.fastdelivery.databinding.DialogSettingWarehouseBinding
import kotlin.collections.ArrayList

class AssignmentMngActivity : AppCompatActivity(),AssignmentItf {
    lateinit var binding : ActivityAssignmentMngBinding
    lateinit var assignmentPst: AssignmentPst
    var adapter : AdapterAdminParcelMng = AdapterAdminParcelMng()
    var dialog: StartAlertDialog = StartAlertDialog()
    var status = ""
    var adress = ""
    var wards = ""
    var listStatus= ArrayList<String>()
    var listDistrict = ArrayList<DistrictRes>()
    var listDistrictName = ArrayList<String>()
    var listWardstName = ArrayList<String>()
    lateinit var dialog2: Dialog
    lateinit var bindingDialog : DialogSettingWarehouseBinding
    var adapterWH : AdapterParcelWh = AdapterParcelWh()
    var parcel_way = ""
    var checkCount = 0
    var idParcel = 0
    lateinit var adressStore : String
    lateinit var adressCustomer: String
    var arrLocationWH : ArrayList<Location> = ArrayList<Location>()
    val storeLocation = Location("Store")
    val customerLocation = Location("Customer")
    lateinit var arrVertWH : ArrayList<Vert>
    lateinit var vertStore : Vert
    lateinit var vertCustomer : Vert
    companion object{
        lateinit var geocoder : Geocoder
        var listWarehouse : List<WarehouseRes> = ArrayList<WarehouseRes>()
        lateinit var arrDistanceWH : Array<DoubleArray>
        lateinit var distanceStore : DoubleArray
        lateinit var distanceCustomer : DoubleArray
        //filter
        var listParcel = ArrayList<AdGetParcelRes>()
        lateinit var listFilter : ArrayList<AdGetParcelRes>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        assignmentPst = AssignmentPst(this)
        geocoder = Geocoder(this)
        dialog2 = Dialog(this@AssignmentMngActivity)
        bindingDialog = DialogSettingWarehouseBinding.inflate(layoutInflater)
        dialog2.setContentView(bindingDialog.root)

        //phân bưu kiện về kho có đường đi ngắn nhất
        assignmentWarehouse()
        setData()
        setEvent()
        setToolbar()
        callInvokeDijkstra()
        callInvokeSetting()
        callInvokeCheckBoxTrue()
        callInvokeCheckBoxFalse()
    }

    fun setData(){
        assignmentPst.getListStatus()
        var req = AdGetParcelReq(adress,status)
        assignmentPst.filterParcel(req)
        assignmentPst.getDistrict()
    }

    fun assignmentWarehouse(){
        assignmentPst.getListWarehouse()
    }

    fun setEvent(){
        binding.edtSearchId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var str = binding.edtSearchId.text.toString()
                assignmentPst.getFilter(str)
            }
        })

        binding.spinnerDistrict.setOnItemClickListener(({adapterView, view, i , l ->
            adress = adapterView.getItemAtPosition(i).toString()
            if(adress.equals("Tất cả")){
                adress = ""
                val mlist = mutableListOf("")
                mlist.clear()
                setAdapterWards(mlist)
                binding.spinnerWards.setText("")
            }
            else{
                for(list in listDistrict){
                    if(list.name.equals(adapterView.getItemAtPosition(i).toString())){
                        println("code:"+list.code)
                        assignmentPst.getWards(list.code)
                    }
                }
            }
            println("adress: "+adress)
            var req = AdGetParcelReq(adress,status)
            assignmentPst.filterParcel(req)
        }))

        binding.spinnerWards.setOnItemClickListener(({adapterView, view, i , l ->
            wards = adapterView.getItemAtPosition(i).toString()+", "+adress
            println("adress: "+wards)
            var req = AdGetParcelReq(wards,status)
            assignmentPst.filterParcel(req)
        }))

        binding.spinnerStatus.setOnItemClickListener(({adapterView, view, i , l ->
            status = adapterView.getItemAtPosition(i).toString()
            if(status.equals("Tất cả")){
                status = ""
            }
            println("status: "+status)
            var req = AdGetParcelReq(adress,status)
            assignmentPst.filterParcel(req)
        }))

        binding.lnlHome.setOnClickListener{
            binding.edtSearchId.clearFocus()
            binding.lnlHome.hideKeyboard()
        }

        binding.refresh.setOnClickListener{
            var req = AdGetParcelReq("","")
            assignmentPst.filterParcel(req)
            binding.spinnerDistrict.setText("",false)
            binding.spinnerStatus.setText("",false)
            binding.spinnerWards.setText("",false)
        }

    }

    fun initArrLocation(geocoder: Geocoder){
        println("init")
        //init warehouse location
        arrVertWH =  ArrayList<Vert>()
        for(warehouse in listWarehouse){
            var location = Location(warehouse.tenkho)
            assignmentPst.convertAdressToLocation(geocoder,warehouse.diachi,location)
            arrLocationWH.add(location)
            arrVertWH.add(Vert(warehouse.tenkho))
        }
        //init store location
        //viết api call địa chỉ cửa hàng từ mach trên bưu kiện
//        assignmentPst.convertAdressToLocation(geocoder,"" +
//                "170 Lê Văn Khương, Thới An, Quận 12, Thành phố Hồ Chí Minh",storeLocation)
        assignmentPst.convertAdressToLocation(geocoder,adressStore,storeLocation)
        println("adressStore"+adressStore)
        //init customer location
        //lấy địa chỉ người nhận trên bưu kiện
//        assignmentPst.convertAdressToLocation(geocoder,"" +
//                "32 Đường Số 6, Phường Linh Trung, Thủ Đức, Thành phố Hồ Chí Minh",customerLocation)
        assignmentPst.convertAdressToLocation(geocoder,adressCustomer,customerLocation)
        println("adressCustomer"+adressCustomer)

        getDistanceByLocation()
    }

    fun getDistanceByLocation(){
        arrDistanceWH = Array(listWarehouse.size,{DoubleArray(listWarehouse.size)})
        distanceStore = DoubleArray(listWarehouse.size)
        distanceCustomer = DoubleArray(listWarehouse.size)
        var pointStore = storeLocation.latitude.toString()+","+storeLocation.longitude.toString()
        var pointCustomer = customerLocation.latitude.toString()+","+customerLocation.longitude.toString()

    //    set list distance warehouse together, store with warehouse, warehouse with customer
        for(i in 0..listWarehouse.size-1){
            var pointSource = arrLocationWH.get(i).latitude.toString()+","+arrLocationWH.get(i).longitude.toString()

            assignmentPst.callAPIGraphhopperRes(pointStore,pointSource,i,-1) //get distance store -> từng kho
            assignmentPst.callAPIGraphhopperRes(pointSource,pointCustomer,i,-2)//get distance từng kho -> customer
            for(j in 0..listWarehouse.size-1){
                if( i == j ){
                    arrDistanceWH[i][j] = 0.0
                }
                else{
                    var pointDes = arrLocationWH.get(j).latitude.toString()+","+arrLocationWH.get(j).longitude.toString()
                    assignmentPst.callAPIGraphhopperRes(pointSource,pointDes,i,j)//get distance các kho với nhau
                }
            }
        }
        Handler().postDelayed({
            setNeighbour()
        }, 4000)
    }

    fun setNeighbour(){
        vertStore = Vert("Cửa hàng")
        vertCustomer = Vert("Khách hàng")
        for(i in 0..listWarehouse.size-1){
            vertStore.addNeighbour(Edge(distanceStore[i],vertStore,arrVertWH.get(i)))
            println("Cửa hàng --- "+arrVertWH.get(i).getName()+": "+distanceStore[i])
            arrVertWH.get(i).addNeighbour(Edge(distanceCustomer[i],arrVertWH.get(i),vertCustomer))
            println(arrVertWH.get(i).getName()+" --- "+"Khách hàng: "+distanceCustomer[i])
            for(j in 0..listWarehouse.size-1){
                if( i != j ){
                    arrVertWH.get(i).addNeighbour(Edge(arrDistanceWH[i][j],arrVertWH.get(i),arrVertWH.get(j)))
                    println(arrVertWH.get(i).getName()+" --- "+arrVertWH.get(j).getName()+": "+arrDistanceWH[i][j])
                }
            }
        }

        assignmentPst.ShortestP(vertStore)
        System.out.println("Khoảng cách tối thiểu từ Store đến custumer: " + String.format("%.2f",vertCustomer.getDist()))
        System.out.println("Đường đi ngắn nhất từ store đến customer: " + assignmentPst.getShortestP(vertCustomer,vertStore))
        var str = assignmentPst.getShortestP(vertCustomer,vertStore).toString()
        var mess = "Bưu kiện "+idParcel+" có lộ trình như sau:\n"+
                str.substring(1,str.length-1).replace(", "," -> ").trim()+"\nBạn có chấp nhận?"

        dialog.showStartDialog4(mess, this)
        dialog.clickOk = { ->
            str = str.substring(vertStore.getName().length+2,str.length-vertCustomer.getName().length-3).trim()
            val arrWord = str.split(", ")
            var stt = 0  //tạo biến đếm để cho kho đầu tiên nhìn thấy
            for (word in arrWord) {
                println("Word: "+word)
                for(x in listWarehouse){
                    if(word.equals(x.tenkho)){
                        //    way = way + x.tenkho
                        println("mã kho: "+x.makho)
                        //call api lưu vào đường đi
                        var req = WayReq(idParcel,x.makho,stt)
                        assignmentPst.addWay(req, arrWord.size-1) //truyền vào size của các kho đi qua
                    }
                }
                stt++
            }
        }
    }
    
    fun callInvokeDijkstra(){
        adressStore = ""
        adressCustomer = ""
        adapter.clickDijkstra = {
            data ->
            adressCustomer = data.diachinguoinhan
            idParcel = data.mabk
            assignmentPst.getShopDetail(GetShopDetailReq(data.mach))
        }
    }

    fun callInvokeSetting(){
        adapter.clickSetting = {
            data -> assignmentPst.getParcelWh(GetParcelWhReq("Đang lưu kho"))
            idParcel = data.mabk
        }
    }

    fun callInvokeCheckBoxTrue(){
        adapterWH.clickCheckboxTrue = {
            data, count ->
            checkCount = checkCount + count
            println("checkCount: "+checkCount.toString())
            parcel_way = parcel_way + data.tenkho + " -> "
            bindingDialog.tvParcelWay.setText("Cửa hàng -> "+parcel_way+"Khách hàng")
        }
    }

    fun callInvokeCheckBoxFalse(){
        adapterWH.clickCheckboxFalse = {
                data, count ->
            checkCount = checkCount + count
            println("checkCount: "+checkCount.toString())
            parcel_way = parcel_way.replace(data.tenkho+" -> ","")
            bindingDialog.tvParcelWay.setText("Cửa hàng -> "+parcel_way+"Khách hàng")
        }
    }

    fun showDialogSetting(gravity : Int, list : List<GetParcelWhRes>){
        val window = dialog2.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.CENTER == gravity) {
            dialog2.setCancelable(false)
        } else {
            dialog2.setCancelable(false)
        }

        adapterWH.setData(list)
        bindingDialog.rcvListParcelWh.adapter = adapterWH
        bindingDialog.rcvListParcelWh.layoutManager =  LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

        bindingDialog.btnAccept.setOnClickListener{
            println("checkCount: "+checkCount.toString())
            if(checkCount == 0){
                dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.mes_warehouse_empty),this)
            }else{
                var str = bindingDialog.tvParcelWay.text.toString()
                str = str.substring(11,str.length-13).trim()
                println(str)
                val arrWord = str.split(" -> ")
                var stt = 0  //tạo biến đếm để cho kho đầu tiên nhìn thấy
                for (word in arrWord) {
                    for(x in listWarehouse){
                        if(word.equals(x.tenkho)){
                            //call api lưu vào đường đi
                            var req = WayReq(idParcel,x.makho,stt)
                            assignmentPst.addWay(req, arrWord.size-1) //truyền vào size của các kho đi qua
                        }
                    }
                    stt++
                }
                parcel_way = ""
                bindingDialog.tvParcelWay.setText("")
                checkCount = 0
                dialog2.dismiss()
            }
        }

        bindingDialog.btnCancel.setOnClickListener{
            parcel_way = ""
            bindingDialog.tvParcelWay.setText("")
            checkCount = 0
            dialog2.dismiss()
        }

        dialog2.show()
    }

    override fun getShopDetail(res: GetShopDetailRes) {
        adressStore = res.diachi
        initArrLocation(geocoder)
    }

    override fun addWaySuccess() {
        dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.mes_assignment_success),this)
        var req = AdGetParcelReq(adress,status)
        assignmentPst.filterParcel(req)
    }

    override fun getParcelWh(list: List<GetParcelWhRes>) {
        showDialogSetting(Gravity.CENTER, list)
    }

    override fun getListWarehouse() {
        //    initArrLocation(geocoder)
    }

    override fun getListStatus(list: List<ListStatusRes>) {
        listStatus.add("Tất cả")
        for(i in 0..list.size-1){
            listStatus.add(list.get(i).tentrangthai)
        }
        setAdapterStatus(listStatus)
    }

    fun setAdapterStatus(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerStatus.setAdapter(adapter)
        binding.spinnerStatus.setHint("Tất cả")
    }

    override fun getListDistrict(list: List<DistrictRes>) {
        listDistrict = list as ArrayList<DistrictRes>
        listDistrictName.add("Tất cả")
        for(i in 0..list.size-1){
            listDistrictName.add(list.get(i).name)
        }
        setAdapterDistrict(listDistrictName)
    }

    fun setAdapterDistrict(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerDistrict.setAdapter(adapter)
        binding.spinnerDistrict.setHint("Tất cả")
        binding.spinnerWards.setHint("Tất cả")
    }

    override fun getListWards(list: List<WardsRes>) {
        listWardstName.add("Tất cả")
        for(i in 0..list.size-1){
            listWardstName.add(list.get(i).name)
        }
        setAdapterWards(listWardstName)
    }

    fun setAdapterWards(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerWards.setAdapter(adapter)
        binding.spinnerWards.setHint("Tất cả")
    }

    override fun getListParcel(list: List<AdGetParcelRes>) {
        adapter.setData(list)
        binding.rcvListParcel.adapter = adapter
        binding.rcvListParcel.layoutManager =  LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    fun setToolbar() {
        var toolbar = binding.toolbarAdminParcel
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
        var req = AdGetParcelReq(adress,status)
        assignmentPst.filterParcel(req)
        binding.edtSearchId.setText("")
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