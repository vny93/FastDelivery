package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.detailParcel.GetDetailParcelRes
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaRes
import vn.vunganyen.fastdelivery.databinding.ItemDetailParcelBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*


class AdapterDetailParcel : RecyclerView.Adapter<AdapterDetailParcel.MainViewHolder>() {
    private var listData: List<GetDetailParcelRes> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<GetDetailParcelRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemDetailParcelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: GetDetailParcelRes) {
            binding.tvProname.setText(data.tensp)
            val tv_price = SplashActivity.formatterPrice.format(data.giatien).toString() + " đ"
            binding.tvProPrice.setText(tv_price)
            binding.tvAmount.setText("x"+data.soluong)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemDetailParcelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = listData[position]
        holder.bindItem(data)

    }

}