package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.parcel.FullStatusDetailRes
import vn.vunganyen.fastdelivery.databinding.ItemStatusDetailBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterStatusDetail : RecyclerView.Adapter<AdapterStatusDetail.MainViewHolder>() {
    private var listData: List<FullStatusDetailRes> = ArrayList()
    var c = Calendar.getInstance()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<FullStatusDetailRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemStatusDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: FullStatusDetailRes) {
            binding.tvStatus.setText(data.tentrangthai)
            if(data.nvkho != null){
                binding.tvNameStaff.visibility = View.VISIBLE
                binding.tvNameStaff.setText("Nhân viên kho: "+data.manv+" - "+data.nvkho+" - "+data.sdtnvkho)
            }
            else binding.tvNameStaff.visibility = View.GONE

            if(data.shipper != null){
                binding.tvNameShipper.visibility = View.VISIBLE
                binding.tvNameShipper.setText("Shipper: "+data.mashipper+" - "+data.shipper+" - "+data.sdtshipper)
            }
            else binding.tvNameShipper.visibility = View.GONE

            var mdate: Date = SplashActivity.formatdate2.parse(data.tgcapnhattrangthai)
            c.time = mdate
            c.add(Calendar.DATE, 1) // number of days to add
            var strDate2 = SplashActivity.formatdate3.format(c.time)
            binding.tvTime.setText(strDate2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemStatusDetailBinding.inflate(
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
//            var intent = Intent(holder.itemView.context, ParcelDetailActivity::class.java)
//            intent.putExtra("data", data)
//            holder.itemView.context.startActivity(intent)
        }

    }
}