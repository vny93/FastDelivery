package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.parcel.StGetParcelRes
import vn.vunganyen.fastdelivery.databinding.ItemStaffParcelBinding
import vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng.ParcelDetailActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.parcelDetail.StaffParcelDetailActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterStaffParcelMng : RecyclerView.Adapter<AdapterStaffParcelMng.MainViewHolder>() {
    private var listData: List<StGetParcelRes> = ArrayList()
    var c = Calendar.getInstance()
    var clickConfirm: ((data : StGetParcelRes)->Unit)?=null
    var clickGetTheShop: ((data : StGetParcelRes)->Unit)?=null
    var clickSaveWarehouse: ((data : StGetParcelRes)->Unit)?=null
    var clickGetCustomer: ((data : StGetParcelRes)->Unit)?=null
    var clickConfirmSucess: ((data : StGetParcelRes)->Unit)?=null
    var clickConfirmFail: ((data : StGetParcelRes)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<StGetParcelRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemStaffParcelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: StGetParcelRes) {
            binding.tvstIdPc.setText("Mã: " + data.mabk.toString())
            binding.tvstStatusPc.setText(data.tentrangthai)
            binding.tvstNamePc.setText(data.hotennguoinhan)
            binding.tvstPhonePc.setText(data.sdtnguoinhan)
            binding.tvstDeliveryPc.setText(data.htvanchuyen)
            var mdate: Date = SplashActivity.formatdate2.parse(data.ngaygui)
            c.time = mdate
            c.add(Calendar.DATE, 1) // number of days to add
            var strDate2 = SplashActivity.formatdate4.format(c.time)
            binding.tvstDatePc.setText(strDate2)

            if(data.tentrangthai.equals("Chờ xác nhận")){
                binding.btnstActionPc.setText("Xác nhận")

            }else if(data.tentrangthai.equals("Đã xác nhận") || data.tentrangthai.equals("Từ chối lấy hàng")){
                binding.btnstActionPc.setText("Phân lấy hàng")

            }else if(data.tentrangthai.equals("Đang lấy hàng")){
                binding.btnstActionPc.visibility = View.GONE

            }else if(data.tentrangthai.equals("Lấy hàng thành công")){
                if(data.htvanchuyen.equals("Giao hàng trong 2h")){
                    binding.btnstActionPc.visibility = View.GONE
                }
                else binding.btnstActionPc.setText("Lưu kho")

            }else if(data.tentrangthai.equals("Đang lưu kho") || data.tentrangthai.equals("Từ chối giao hàng")){
                binding.btnstActionPc.setText("Phân giao hàng")

            }else if(data.tentrangthai.equals("Đang xuất kho") ||
                    data.tentrangthai.equals("Đang giao hàng")){
                binding.btnstActionPc.visibility = View.GONE

            }else if(data.tentrangthai.equals("Giao hàng thành công")){
                binding.btnstActionPc.setText("Xác nhận giao thành công") //update mã nhân viên vào dòng trạng thái giao thành công

            }else if(data.tentrangthai.equals("Giao hàng thất bại")){
                //phân nv khác giao
                //khi xác nhận giao thất bại sẽ check đã giao >=2 lần chưa,
                //nếu 2 lần hiện thông báo hoàn trả
                if(data.manv.isEmpty()){
                    binding.btnstActionPc.setText("Xác nhận giao thất bại") //update mã nhân viên vào dòng trạng thái giao thất bại
                }
                else{
                    //có nên phân giao hàng lại kh, hay nv cũ sẽ tiếp nhận ???
                    binding.btnstActionPc.setText("Phân giao hàng")
                }

            }else if(data.tentrangthai.equals("Khách hàng hủy")){
                binding.btnstActionPc.setText("Hoàn trả")
            }//....còn thêm
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
        holder.itemView.setOnClickListener{
            var intent = Intent(holder.itemView.context, StaffParcelDetailActivity::class.java)
           intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.btnstActionPc.setOnClickListener{
            if(holder.binding.btnstActionPc.text.equals("Xác nhận")){
                println("Đã xác nhận")
                clickConfirm?.invoke(data)
            }
            else if(holder.binding.btnstActionPc.text.equals("Phân lấy hàng")){
                println("Giao cho shipper")
                clickGetTheShop?.invoke(data)
            }
            else if(holder.binding.btnstActionPc.text.equals("Lưu kho")){
                println("Lưu kho")
                clickSaveWarehouse?.invoke(data)
            }
            else if(holder.binding.btnstActionPc.text.equals("Phân giao hàng")){
                println("Giao cho shipper")
                clickGetCustomer?.invoke(data)
            }
            else if(holder.binding.btnstActionPc.text.equals("Xác nhận giao thành công")){
                println("Xác nhận")
                clickConfirmSucess?.invoke(data)
            }
            else if(holder.binding.btnstActionPc.text.equals("Xác nhận giao thất bại")){
                println("Xác nhận")
                clickConfirmFail?.invoke(data)
            }
        }

    }
}