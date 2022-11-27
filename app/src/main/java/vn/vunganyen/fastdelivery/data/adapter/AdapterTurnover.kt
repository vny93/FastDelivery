package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.turnover.TurnoverRes
import vn.vunganyen.fastdelivery.databinding.ItemAdminTurnoverBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.text.DecimalFormat
import java.util.*

class AdapterTurnover : RecyclerView.Adapter<AdapterTurnover.MainViewHolder>() {
    private var listData: List<TurnoverRes> = ArrayList()
    fun setData(list: List<TurnoverRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemAdminTurnoverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: TurnoverRes) {
            var mdate : Date = SplashActivity.formatMonth.parse(data.thang.toString())
            var month = SplashActivity.formatMonth.format(mdate)
            binding.time.setText(month+"/"+data.nam.toString())
            val price = SplashActivity.formatter.format(data.doanhthu).toString() + " đ"
            binding.priceStatistics.setText(price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemAdminTurnoverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
    }
}