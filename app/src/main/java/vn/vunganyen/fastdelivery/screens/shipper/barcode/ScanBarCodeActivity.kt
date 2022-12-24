package vn.vunganyen.fastdelivery.screens.shipper.barcode

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelReq
import vn.vunganyen.fastdelivery.data.model.parcel.GetDetailParcelRes
import vn.vunganyen.fastdelivery.databinding.ActivityScanBarCodeBinding
import vn.vunganyen.fastdelivery.screens.shop.homeShop.HomeShopActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.Exception
import java.util.*

class ScanBarCodeActivity : AppCompatActivity(), ScanBarCodeItf {
    lateinit var binding : ActivityScanBarCodeBinding
    lateinit var scanBarCodePst: ScanBarCodePst
    private val SELECT_PHOTO = 100
    var barcode: String? = null
    var c = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBarCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scanBarCodePst = ScanBarCodePst(this)
        builder = AlertDialog.Builder(this)
        setToolbar()
        setEvent()
    }

    fun setEvent() {
        binding.imvScan.setOnClickListener {
            val photoPic = Intent(Intent.ACTION_PICK)
            photoPic.type = "image/*"
            startActivityForResult(photoPic, SELECT_PHOTO)
        }
    }

    @SuppressLint("ResourceType")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_PHOTO ->
                if (resultCode == RESULT_OK) {
                    //doing some uri parsing
                    val selectedImage: Uri? = data?.getData()
                    var imageStream: InputStream? = null
                    try {
                        //getting the image
                        imageStream = contentResolver.openInputStream(selectedImage!!)
                    } catch (e: FileNotFoundException) {
                        Toast.makeText(applicationContext, "File not found", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                    val bMap = BitmapFactory.decodeStream(imageStream)
                    binding.imvScan.setBackgroundResource(R.color.white)
                    binding.imvScan.setImageURI(selectedImage)
                    val intArray = IntArray(bMap.width * bMap.height)
                    // copy pixel data from the Bitmap into the 'intArray' array
                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
                    val source: LuminanceSource = RGBLuminanceSource(bMap.width, bMap.height, intArray)
                    val bitmap = BinaryBitmap(HybridBinarizer(source))
                    val reader: Reader = MultiFormatReader() // use this otherwise
                    try {
                        val decodeHints = Hashtable<DecodeHintType, Any>()
                        decodeHints.put(DecodeHintType.TRY_HARDER, true);
                        decodeHints.put(DecodeHintType.PURE_BARCODE, true);
                        val result = reader.decode(bitmap, decodeHints)
                        barcode =  result.getText().toString();
                        if(barcode!=null){
                            try {
                                scanBarCodePst.getDetailParcel(GetDetailParcelReq(barcode.toString().toInt()))
                            }
                            catch (e : Exception){
                                builder.setTitle("Kết quả");
                                builder.setMessage(barcode);
                                val alert1: android.app.AlertDialog? = builder.create()
                                alert1!!.setButton(
                                    DialogInterface.BUTTON_POSITIVE, "Đóng") { dialog, which ->
                                    //val i = Intent(baseContext, MainActivity::class.java)
                                    // startActivity(i)
                                }
                                alert1.setCanceledOnTouchOutside(false);
                                Handler().postDelayed({
                                    alert1.show();
                                }, 1000)
                            }

                        }
                        else{
                            builder.setTitle("Kết quả")
                            //  builder.setIcon(R.mipmap.ic_launcher)
                            builder.setMessage("Không tìm thấy kết quả hãy thử một hình ảnh khác hoặc thử lại")
                            val alert1: AlertDialog = builder.create()
                            alert1.setButton(
                                DialogInterface.BUTTON_POSITIVE, "Đóng") { dialog, which ->
                            }
                            alert1.setCanceledOnTouchOutside(false)
                            alert1.show()
                        }
                    }
                    catch (e : NotFoundException) {
                        Toast.makeText(getApplicationContext(), "Nothing Found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (e : ChecksumException) {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch ( e: FormatException) {
                        Toast.makeText(getApplicationContext(), "Wrong Barcode/QR format", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch ( e:NullPointerException) {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
        }

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

    override fun getDetailParcel(res: GetDetailParcelRes) {
        builder.setTitle("Kết quả");
        // builder.setIcon(R.mipmap.ic_launcher);
        var mdate: Date = SplashActivity.formatdate2.parse(res.ngaygui)
        c.time = mdate
        c.add(Calendar.HOUR_OF_DAY, 7)
        //  c.add(Calendar.DATE, 1) // number of days to add
        var strDate = SplashActivity.formatdate4.format(c.time)
        val money = SplashActivity.formatter.format(res.sotien).toString() + " đ"
        builder.setMessage("Mã bưu kiện: "+res.mabk +
                "\nNgười nhận: "+res.hotennguoinhan+
                "\nSố điện thoại: "+res.sdtnguoinhan+
                "\nĐịa chỉ người nhận: "+res.diachinguoinhan+
                "\nPhương thức thanh toán: "+res.ptthanhtoan+
                "\nSố tiền: "+money+
                "\nTình trạng thanh toán: "+res.tinhtrangthanhtoan+
                "\nKhối lượng: "+res.khoiluong+
                "\nKích thước: "+res.kichthuoc+
                "\nPhí giao: "+res.phigiao+
                "\nHình thức vận chuyễn: "+res.htvanchuyen+
                "\nGhi chú: "+res.ghichu+
                "\nNgày gửi: "+strDate)
        val alert1: android.app.AlertDialog? = builder.create()
        alert1!!.setButton(DialogInterface.BUTTON_POSITIVE, "Đóng")
        { dialog, which ->
        }
        alert1.setCanceledOnTouchOutside(false)
        Handler().postDelayed({
            alert1.show();
        }, 1000)
    }
}