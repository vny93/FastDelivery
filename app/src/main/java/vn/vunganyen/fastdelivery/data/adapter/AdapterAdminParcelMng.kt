package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.parcel.AdGetParcelRes
import vn.vunganyen.fastdelivery.databinding.ItemAdminParcelBinding
import vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng.ParcelDetailActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterAdminParcelMng : RecyclerView.Adapter<AdapterAdminParcelMng.MainViewHolder>() {
    private var listData: List<AdGetParcelRes> = ArrayList()
    var c = GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
    var clickDijkstra: ((data : AdGetParcelRes)->Unit)?=null
    var clickSetting: ((data : AdGetParcelRes)->Unit)?=null
    var generateBarcode : ((id: Int) -> Unit)? = null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<AdGetParcelRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemAdminParcelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: AdGetParcelRes) {
            binding.tvadIdPc.setText("Mã: " + data.mabk.toString())
            binding.tvadStatusPc.setText(data.tentrangthai)
            binding.tvadNamePc.setText("Người nhận: "+data.hotennguoinhan)
            binding.tvadPhonePc.setText("SDT: "+data.sdtnguoinhan)
            binding.tvadDeleveriPc.setText(data.htvanchuyen)
            var mdate: Date = SplashActivity.formatdate2.parse(data.ngaygui)
            c.time = mdate
            c.add(Calendar.HOUR_OF_DAY, 7)
          //  c.add(Calendar.DATE, 1) // number of days to add
            var strDate2 = SplashActivity.formatdate4.format(c.time)
            binding.tvadDatePc.setText("Ngày cửa hàng gửi: "+strDate2)

            if (data.phankho == 0) {
                binding.btnadActionPc.setText("Tự động Phân kho")
                binding.btnSetting.setText("Phân kho")
            } else {
                binding.btnadActionPc.visibility = View.GONE
                binding.btnSetting.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemAdminParcelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
        holder.itemView.setOnClickListener{
            var intent = Intent(holder.itemView.context, ParcelDetailActivity::class.java)
           intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.btnadActionPc.setOnClickListener{
            clickDijkstra?.invoke(data)
        }

        holder.binding.btnSetting.setOnClickListener{
            clickSetting?.invoke(data)
        }

        holder.binding.tvadIdPc.setOnClickListener{
            if(!data.tentrangthai.equals("Chờ xác nhận") && !data.tentrangthai.equals("Đã xác nhận") &&
                !data.tentrangthai.equals("Chờ lấy hàng") && !data.tentrangthai.equals("Từ chối lấy hàng") &&
                !data.tentrangthai.equals("Đang lấy hàng") && !data.tentrangthai.equals("Lấy hàng thành công")){
                generateBarcode?.invoke(data.mabk)
            }
        }
    }
}