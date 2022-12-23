package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.data.model.parcel.StaffGetParcelRes
import vn.vunganyen.fastdelivery.databinding.ItemStaffParcelBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.parcelDetail.StaffParcelDetailActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterStaffParcelMng : RecyclerView.Adapter<AdapterStaffParcelMng.MainViewHolder>() {
    private var listData: List<StaffGetParcelRes> = ArrayList()
    var c = Calendar.getInstance()
    var clickConfirm: ((data: StaffGetParcelRes) -> Unit)? = null
    var clickGetTheShop: ((data: StaffGetParcelRes) -> Unit)? = null
    var clickSaveWarehouse: ((data: StaffGetParcelRes) -> Unit)? = null
    var clickGetCustomer: ((data: StaffGetParcelRes) -> Unit)? = null
    var clickOutWarehouse: ((data: StaffGetParcelRes) -> Unit)? = null
    var clickReturn: ((data: StaffGetParcelRes) -> Unit)? = null
    var clickCancelInfor: ((data: StaffGetParcelRes) -> Unit)? = null

    //new code
    var AutoGetTheShop: ((data: StaffGetParcelRes) -> Unit)? = null
    var AutoReturn: ((data: StaffGetParcelRes) -> Unit)? = null
    var AutoCustomer: ((data: StaffGetParcelRes) -> Unit)? = null
    var generateBarcode : ((id: Int) -> Unit)? = null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<StaffGetParcelRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemStaffParcelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: StaffGetParcelRes) {
            binding.tvstIdPc.setText("Mã: " + data.mabk.toString())
            binding.tvstStatusPc.setText(data.tentrangthai)
            binding.tvstNamePc.setText("Người nhận: " + data.hotennguoinhan)
            binding.tvstPhonePc.setText("SDT: " + data.sdtnguoinhan)
            binding.tvstDeliveryPc.setText(data.htvanchuyen)
            var mdate: Date = SplashActivity.formatdate2.parse(data.ngaygui)
            c.time = mdate
            c.add(Calendar.HOUR_OF_DAY, 7)
            //   c.add(Calendar.DATE, 1) // number of days to add
            var strDate2 = SplashActivity.formatdate4.format(c.time)
            binding.tvstDatePc.setText("Ngày cửa hàng gửi: " + strDate2)

            //new code---------
            binding.btnstActionPc.visibility = View.VISIBLE
            binding.lnlShare.visibility = View.GONE
            //---------

            if (data.idcheck == 1) {
                if (data.tentrangthai.equals("Chờ xác nhận") || data.tentrangthai.equals("Giao hàng thành công") ||
                    data.tentrangthai.equals("Giao hàng thất bại") || data.tentrangthai.equals("Khách hàng hủy") ||
                    data.tentrangthai.equals("Hoàn trả thành công")) {
                    binding.btnstActionPc.setText("Xác nhận")

                } else if (data.tentrangthai.equals("Đã xác nhận") || data.tentrangthai.equals("Từ chối lấy hàng") ||
                    data.tentrangthai.equals("Đang lưu kho") || data.tentrangthai.equals("Từ chối giao hàng") ||
                    data.tentrangthai.equals("Đang lưu kho chờ hoàn trả") || data.tentrangthai.equals("Từ chối hoàn trả")) {
                    binding.btnstActionPc.setText("Giao")

                    //new code ---------
                    binding.lnlShare.visibility = View.VISIBLE
                    binding.btnstActionPc.visibility = View.GONE
                   // ---------

                } else if (data.tentrangthai.equals("Lấy hàng thành công") || data.tentrangthai.equals("Đã chuyển kho")) {
                    if (!data.htvanchuyen.equals("Giao hàng trong 2h")) {
                        binding.btnstActionPc.setText("Lưu kho")
                    } else binding.btnstActionPc.visibility = View.GONE
                } else if (data.tentrangthai.equals("Đang xuất kho") || data.tentrangthai.equals("Đang xuất kho hoàn trả")) {
                    binding.btnstActionPc.setText("Xuất kho")
                } else {
                    binding.btnstActionPc.visibility = View.GONE
                }
            } else binding.btnstActionPc.visibility = View.GONE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemStaffParcelBinding.inflate(
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
            var temp_ghichu = ""
            var temp_mashipper = ""
            var temp_manv = ""
            if (data.ghichu != null) temp_ghichu = data.ghichu
            if (data.mashipper != null) temp_mashipper = data.mashipper
            if (data.manv != null) temp_manv = data.manv

            var dataDetail = SpGetParcelRes(data.mabk, data.hotennguoinhan, data.sdtnguoinhan, data.diachinguoinhan, data.ptthanhtoan,
                data.sotien, data.tinhtrangthanhtoan, data.khoiluong, data.kichthuoc, data.phigiao, data.htvanchuyen, temp_ghichu,
                data.ngaygui, data.mach, data.thoigian, data.tentrangthai, temp_manv, temp_mashipper)
            intent.putExtra("data", dataDetail)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.btnstActionPc.setOnClickListener {
            if (holder.binding.btnstActionPc.text.equals("Xác nhận")) {
                clickConfirm?.invoke(data)
            }
            /*    else if(holder.binding.btnstActionPc.text.equals("Giao")){
                    if(data.tentrangthai.equals("Đã xác nhận") || data.tentrangthai.equals("Từ chối lấy hàng")){
                        clickGetTheShop?.invoke(data)
                    }
                    else if(data.tentrangthai.equals("Đang lưu kho chờ hoàn trả") || data.tentrangthai.equals("Từ chối hoàn trả")){
                        clickReturn?.invoke(data)
                    }
                    else{
                        clickGetCustomer?.invoke(data)
                    }
                }
            */
            else if (holder.binding.btnstActionPc.text.equals("Lưu kho")) {
                clickSaveWarehouse?.invoke(data)
            } else if (holder.binding.btnstActionPc.text.equals("Xuất kho")) {
                clickOutWarehouse?.invoke(data)
            }
        }
        holder.binding.tvstStatusPc.setOnClickListener {
            if (data.tentrangthai.equals("Từ chối lấy hàng") || data.tentrangthai.equals("Từ chối giao hàng")
                || data.tentrangthai.equals("Từ chối hoàn trả")
            ) {
                clickCancelInfor?.invoke(data)
            }
        }
        //new code ---------
        holder.binding.btnShare.setOnClickListener {
            if (data.tentrangthai.equals("Đã xác nhận") || data.tentrangthai.equals("Từ chối lấy hàng")) {
                clickGetTheShop?.invoke(data)
            } else if (data.tentrangthai.equals("Đang lưu kho chờ hoàn trả") || data.tentrangthai.equals("Từ chối hoàn trả")) {
                clickReturn?.invoke(data)
            } else {
                clickGetCustomer?.invoke(data)
            }
        }
        holder.binding.btnAutoShare.setOnClickListener{
            if (data.tentrangthai.equals("Đã xác nhận") || data.tentrangthai.equals("Từ chối lấy hàng")) {
                AutoGetTheShop?.invoke(data)
            } else if (data.tentrangthai.equals("Đang lưu kho chờ hoàn trả") || data.tentrangthai.equals("Từ chối hoàn trả")) {
                AutoReturn?.invoke(data)
            } else {
                AutoCustomer?.invoke(data)
            }
        }
        //---------
        holder.binding.tvstIdPc.setOnClickListener{
            if(!data.tentrangthai.equals("Chờ xác nhận") && !data.tentrangthai.equals("Đã xác nhận") &&
                    !data.tentrangthai.equals("Chờ lấy hàng") && !data.tentrangthai.equals("Từ chối lấy hàng") &&
                !data.tentrangthai.equals("Đang lấy hàng") && !data.tentrangthai.equals("Lấy hàng thành công")){
                generateBarcode?.invoke(data.mabk)
            }
        }
    }
}