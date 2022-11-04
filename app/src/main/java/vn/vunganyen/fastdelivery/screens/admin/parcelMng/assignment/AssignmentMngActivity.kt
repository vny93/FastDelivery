package vn.vunganyen.fastdelivery.screens.admin.parcelMng.assignment

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import demo.kotlin.model.dijkstra.Edge
import demo.kotlin.model.dijkstra.Vert
import vn.vunganyen.fastdelivery.data.adapter.AdapterAdminParcelMng
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.WardsRes
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelRes
import vn.vunganyen.fastdelivery.data.model.role.ListRoleRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.status.ListStatusRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.data.model.way.WayReq
import vn.vunganyen.fastdelivery.databinding.ActivityAssignmentMngBinding
import vn.vunganyen.fastdelivery.screens.login.LoginActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*
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

    var idParcel = 0
    lateinit var adressStore : String
    lateinit var adressCustomer: String
    var arrLocationWH : ArrayList<Location> = ArrayList<Location>()
    val storeLocation = Location("Store")
    val customerLocation = Location("Customer")
    lateinit var arrVertWH : ArrayList<Vert> //= ArrayList<Vert>()
    lateinit var vertStore : Vert//= Vert("Store")
    lateinit var vertCustomer : Vert //= Vert("Customer")
    companion object{
        lateinit var geocoder : Geocoder
        var listWarehouse : List<WarehouseRes> = ArrayList<WarehouseRes>()
        lateinit var arrDistanceWH : Array<DoubleArray>
        lateinit var distanceStore : DoubleArray
        lateinit var distanceCustomer : DoubleArray

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        assignmentPst = AssignmentPst(this)
        geocoder = Geocoder(this)

        //phân bưu kiện về kho có đường đi ngắn nhất
        assignmentWarehouse()
        setData()
        setEvent()
        setToolbar()
        callInvokeDijkstra()

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
        binding.spinnerDistrict.setOnItemClickListener(({adapterView, view, i , l ->
            adress = adapterView.getItemAtPosition(i).toString()
            for(list in listDistrict){
                if(list.name.equals(adapterView.getItemAtPosition(i).toString())){
                    println("code:"+list.code)
                    assignmentPst.getWards(list.code)
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
            println("status: "+status)
            var req = AdGetParcelReq(adress,status)
            assignmentPst.filterParcel(req)
        }))

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
        vertStore = Vert("Store")
        vertCustomer = Vert("Customer")
        for(i in 0..listWarehouse.size-1){
            vertStore.addNeighbour(Edge(distanceStore[i],vertStore,arrVertWH.get(i)))
            println("Store --- "+arrVertWH.get(i).getName()+": "+distanceStore[i])
            arrVertWH.get(i).addNeighbour(Edge(distanceCustomer[i],arrVertWH.get(i),vertCustomer))
            println(arrVertWH.get(i).getName()+" --- "+"Customer: "+distanceCustomer[i])
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
        str = str.substring(vertStore.getName().length+2,str.length-vertCustomer.getName().length-3).trim()
        val arrWord = str.split(", ")
        var stt = 0  //tạo biến đếm để cho kho đầu tiên nhìn thấy
        //     var way = "Bưu kiện "+idParcel+" sẽ đi từ :"
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

    override fun getShopDetail(res: GetShopDetailRes) {
        adressStore = res.diachi
        initArrLocation(geocoder)
    }

    override fun addWaySuccess() {
        dialog.showStartDialog3(getString(vn.vunganyen.fastdelivery.R.string.mes_assignment_success),this)
        var req = AdGetParcelReq(adress,status)
        assignmentPst.filterParcel(req)
    }

    override fun getListWarehouse() {
        //    initArrLocation(geocoder)
    }

    override fun getListStatus(list: List<ListStatusRes>) {
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
        for(i in 0..list.size-1){
            listDistrictName.add(list.get(i).name)
        }
        setAdapterDistrict(listDistrictName)
    }

    fun setAdapterDistrict(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerDistrict.setAdapter(adapter)
        binding.spinnerDistrict.setHint("Tất cả")
    }

    override fun getListWards(list: List<WardsRes>) {
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
    }
}