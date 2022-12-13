package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.parcel.SpGetParcelRes
import vn.vunganyen.fastdelivery.databinding.ItemShipperParcelBinding
import vn.vunganyen.fastdelivery.screens.admin.parcelMng.detailParcelMng.ParcelDetailActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import vn.vunganyen.fastdelivery.screens.staff.parcelDetail.StaffParcelDetailActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterShopParcelMng : RecyclerView.Adapter<AdapterShopParcelMng.MainViewHolder>() {
    private var listData: List<SpGetParcelRes> = ArrayList()
    var c = Calendar.getInstance()
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
            binding.btnspWayPc.visibility = View.GONE
            binding.btnspActionPc.visibility = View.GONE
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

    }
}