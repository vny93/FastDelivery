package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.databinding.ItemShipperParcelBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.parcelDetail.StaffParcelDetailActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterShipperParcelMng : RecyclerView.Adapter<AdapterShipperParcelMng.MainViewHolder>() {
    private var listData: List<SpGetParcelRes> = ArrayList()
    var c = Calendar.getInstance()
    var clickCancelPickParcel: ((data: SpGetParcelRes) -> Unit)? = null
    var clickGetShopDone: ((data: SpGetParcelRes) -> Unit)? = null
    var clickDelivering: ((data: SpGetParcelRes) -> Unit)? = null
    var clickGetShop : ((data: SpGetParcelRes) -> Unit)? = null
    var clickDelived : ((data: SpGetParcelRes) -> Unit)? = null
    var clickOutWarehouse : ((data: SpGetParcelRes) -> Unit)? = null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<SpGetParcelRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemShipperParcelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: SpGetParcelRes) {
            binding.tvspIdPc.setText("Mã: " + data.mabk.toString())
            binding.tvspStatusPc.setText(data.tentrangthai)
            binding.tvspNamePc.setText(data.hotennguoinhan)
            binding.tvspPhonePc.setText(data.sdtnguoinhan)
            binding.tvspDeliveriPc.setText(data.htvanchuyen)
            var mdate: Date = SplashActivity.formatdate2.parse(data.ngaygui)
            c.time = mdate
            c.add(Calendar.DATE, 1) // number of days to add
            var strDate2 = SplashActivity.formatdate4.format(c.time)
            binding.tvspDatePc.setText(strDate2)


            if (data.tentrangthai.equals("Từ chối lấy hàng") || data.tentrangthai.equals("Từ chối giao hàng") ||
                data.tentrangthai.equals("Giao hàng thành công") || data.tentrangthai.equals("Giao hàng thất bại") ||
                data.tentrangthai.equals("Khách hàng hủy") || data.tentrangthai.equals("Đã chuyển kho")) {

                binding.btnspActionPc.visibility = View.GONE
                binding.btnspWayPc.visibility = View.GONE
            }
            else if(data.tentrangthai.equals("Lấy hàng thành công") ){
                if(!data.htvanchuyen.equals("Giao hàng trong 2h")) {
                    binding.btnspActionPc.visibility = View.GONE
                    binding.btnspWayPc.visibility = View.GONE
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemShipperParcelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
        holder.itemView.setOnClickListener {
            var intent = Intent(holder.itemView.context, StaffParcelDetailActivity::class.java)
            intent.putExtra("data", data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.btnspActionPc.setOnClickListener {
            if (data.tentrangthai.equals("Đang lấy hàng")) {
                //cho cập nhật : Đã lấy hàng hay từ chối lấy hàng
                clickGetShop?.invoke(data)
            }
            else  if (data.tentrangthai.equals("Đang lấy hàng")) {
                //cho cập nhật : Đã lấy hàng hay từ chối lấy hàng
                clickGetShop?.invoke(data)
            }
            else if (data.tentrangthai.equals("Lấy hàng thành công")) {
                //vì ở trên đã xét nếu trong 2h thì sẽ mở sự kiện này
                //cho cập nhật : Đang giao hàng
                clickDelivering?.invoke(data)
            }
            else if(data.tentrangthai.equals("Đang giao hàng")){
                //cho cập nhật: Giao thành công hay thất bại...
                clickDelived?.invoke(data)
            }
            else if(data.tentrangthai.equals("Đang xuất kho")){
                //cho cập nhật: Từ chối giao hoặc đang đi giao
                clickOutWarehouse?.invoke(data)
            }
        }

    }
}