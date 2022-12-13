package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.parcel.FullStatusDetailRes
import vn.vunganyen.fastdelivery.data.model.parcel.ShipperStatisticsRes
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.databinding.ItemShipperParcelBinding
import vn.vunganyen.fastdelivery.databinding.ItemShipperStatisticsBinding
import vn.vunganyen.fastdelivery.databinding.ItemStatusDetailBinding
import vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng.ParcelDetailActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.parcelDetail.StaffParcelDetailActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterShipperStatistics : RecyclerView.Adapter<AdapterShipperStatistics.MainViewHolder>() {
    private var listData: List<ShipperStatisticsRes> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ShipperStatisticsRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemShipperStatisticsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: ShipperStatisticsRes) {
            binding.tvId.setText(data.mabk.toString())
            binding.tvStatus.setText(data.tentrangthai)
            val price = SplashActivity.formatterPrice.format(data.phigiao).toString() + " đ"
            binding.tvPrice.setText(price)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemShipperStatisticsBinding.inflate(
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