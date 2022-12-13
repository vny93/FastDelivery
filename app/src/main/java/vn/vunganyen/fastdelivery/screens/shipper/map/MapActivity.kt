package vn.vunganyen.fastdelivery.screens.shipper.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.graphhopper.ListPointsReq
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.databinding.ActivityMapBinding
import android.location.Geocoder
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import android.provider.Settings

class MapActivity : AppCompatActivity(),MapItf , OnMapReadyCallback {
    lateinit var binding : ActivityMapBinding
    lateinit var mapPst: MapPst
    lateinit var map : GoogleMap
    val myLocation = Location("My Location")
    lateinit var pointMyLocation : String
    val destinationLocation = Location("Destination Location")
    lateinit var destination: String
    lateinit var geocoder : Geocoder

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(binding.root)
        geocoder = Geocoder(this)
        var mapFragment = fragmentManager.findFragmentById(R.id.myMap) as MapFragment
        mapFragment.getMapAsync(this)
        mapPst = MapPst(this)
        setToolbar()
        getCurrentLocation()
        //getData()
    }

    fun getCurrentLocation(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        binding.apply {
                            println( "Latitude\n${list[0].latitude}")
                            println("Longitude\n${list[0].longitude}")
                            println("Country Name\n${list[0].countryName}" )
                            println("Locality\n${list[0].locality}")
                            println("Address\n${list[0].getAddressLine(0)}")
                            myLocation.latitude = list[0].latitude
                            myLocation.longitude = list[0].longitude
                            getData()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    fun getData(){
        var data = getIntent().getSerializableExtra("data") as SpGetParcelRes
        //lấy tọa độ vị trí hiện tại
      //  myLocation.latitude = 10.835848972953372
      //  myLocation.longitude = 106.65869674590574

        pointMyLocation = myLocation.latitude.toString()+","+myLocation.longitude.toString()
        println("myLocation: "+myLocation.latitude.toString()+","+myLocation.longitude.toString())
        //đổi từ tên địa chỉ đến thành tọa độ số
        if(data.tentrangthai.equals("Đang lấy hàng") || data.tentrangthai.equals("Đang hoàn trả")){
            //lấy địa chỉ cửa hàng
            mapPst.getShopDetail(GetShopDetailReq(data.mach))
        }
        else{
            //lấy địa chỉ khách hàng
            destination = data.diachinguoinhan
            mapPst.convertAdressToLocation(geocoder,destination,destinationLocation)
            println("addressDestination"+destination)
            var pointDestination = destinationLocation.latitude.toString()+","+destinationLocation.longitude.toString()
            mapPst.getMap(pointMyLocation,pointDestination)
        }

    }

    fun decryption(encoder : String){
        var is3D = false
        mapPst.getListPoint(ListPointsReq(encoder, is3D))
    }

    fun moveCamera(location1 : Location, location2: Location){
        var x1 = LatLng(location1.latitude,location1.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(x1, 13F))
        map.addMarker(MarkerOptions()
            .title("Vị trí của tôi")
            .position(x1))

        var x2 = LatLng(location2.latitude,location2.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(x2, 13F))
        map.addMarker(MarkerOptions()
            .title("Vị trí đến")
            .position(x2))
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

        /*
     //   map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(10.7711724,106.7059598),13F))
        var x1 = LatLng(10.7711724,106.7059598)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(x1, 13F))
        map.addMarker(MarkerOptions()
            .title("Vị trí của tôi")
            .snippet("The most...")
            .position(x1))

        var x2 = LatLng(10.837898599999999,106.6713175)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(x2, 13F))
        map.addMarker(MarkerOptions()
            .title("Vị trí đến")
            .snippet("The most...")
            .position(x2)
         */
//        map.addPolyline(
//            PolylineOptions().geodesic(true)
//            .add(LatLng(10.7711724,106.7059598))
//            .add(LatLng(10.77111087632413, 106.70236756831775))
//            .add(LatLng(10.776781239746251, 106.69991066670417))
//            .add(LatLng(10.785866266418243, 106.68997575222406))
//            .add(LatLng(10.785149580078645, 106.6889994377117))
//            .add(LatLng(10.792981525354978, 106.68004266337152))
//            .add(LatLng(10.79583759337839, 106.68187729518093))
//            .add(LatLng(10.837898599999999,106.6713175)))

//
//        var x2 = LatLng(10.837898599999999,106.6713175)
//        map.addPolyline(PolylineOptions().add(x1,x2) // add 1 list điểm đã đc mã hóa từ đoạn code kia vô đây nè
//            .width(10F)
//            .color(Color.BLUE))

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return
//        }
//        map.isMyLocationEnabled = true
//        map.getUiSettings().setMyLocationButtonEnabled(true);
    }

    fun drawLine(list : List<List<Double>>){
        moveCamera(myLocation,destinationLocation)
        var polylineOptions = PolylineOptions().geodesic(true)
        for(x in list){
            polylineOptions.add(LatLng(x.get(1),x.get(0)))
        }
        map.addPolyline(polylineOptions)

    }

    override fun getShopDetail(res: GetShopDetailRes) {
        destination = res.diachi
        mapPst.convertAdressToLocation(geocoder,destination,destinationLocation)
        println("addressDestination"+destination)
        var pointDestination = destinationLocation.latitude.toString()+","+destinationLocation.longitude.toString()
        mapPst.getMap(pointMyLocation,pointDestination)
    }

    override fun getEncoded(encoded: String) {
        decryption(encoded)
    }

    override fun getListPoint(list: List<List<Double>>) {
        drawLine(list)
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
}