package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryRes
import vn.vunganyen.fastdelivery.databinding.ItemShipperSalaryBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*
import kotlin.collections.ArrayList


class AdapterShipperSalaryMng : RecyclerView.Adapter<AdapterShipperSalaryMng.MainViewHolder>() {
    private var listData: List<ShipperSalaryRes> = ArrayList()
    var c = Calendar.getInstance()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ShipperSalaryRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemShipperSalaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: ShipperSalaryRes) {
            var mdate: Date = SplashActivity.formatdate2.parse(data.ngaynhan)
            c.time = mdate
            c.add(Calendar.DATE, 1) // number of days to add
            var strDate2 = SplashActivity.formatdate4.format(c.time)
            binding.tvShipperDate.setText(strDate2)

            var sum = data.luongcoban + data.tienhoahong
            val tv_sum = SplashActivity.formatterPrice.format(sum).toString() + " đ"
            binding.tvSalarySum.setText(tv_sum)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemShipperSalaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
//        holder.itemView.setOnClickListener{
//            var intent = Intent(holder.itemView.context, BrandDetailMngActivity::class.java)
//            intent.putExtra("data",data)
//            holder.itemView.context.startActivity(intent)
//        }
    }
}