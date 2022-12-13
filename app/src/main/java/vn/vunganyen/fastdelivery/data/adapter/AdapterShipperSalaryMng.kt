package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.salary.ShipperSalaryRes
import vn.vunganyen.fastdelivery.databinding.ItemShipperSalaryBinding
import vn.vunganyen.fastdelivery.screens.shipper.shipperStatistics.ShipperStatisticsActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.util.*


class AdapterShipperSalaryMng : RecyclerView.Adapter<AdapterShipperSalaryMng.MainViewHolder>() {
    private var listData: List<ShipperSalaryRes> = ArrayList()
    var c = Calendar.getInstance()
    var cMonth =  GregorianCalendar(TimeZone.getTimeZone("GMT+7"))
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
            cMonth.clear()
            cMonth.time = mdate

            //cộng 7 múi giờ vào trước sau đó mới trừ tháng ra
            cMonth.add(Calendar.HOUR_OF_DAY, 7)
            cMonth.roll(Calendar.MONTH,-1)

            var strDate1 = SplashActivity.formatMonthYear.format(cMonth.time)
            binding.tvSalaryMonth.setText("Lương tháng: "+strDate1)

            c.time = mdate
            c.add(Calendar.HOUR_OF_DAY, 7)
        //    c.add(Calendar.DATE, 1) // number of days to add
            var strDate2 = SplashActivity.formatdate4.format(c.time)
            binding.tvShipperDate.setText(strDate2)

            val tv_sum = SplashActivity.formatterPrice.format(data.luongcoban).toString() + " đ"
            binding.tvSalarySum.setText(tv_sum)

            if(SplashActivity.profile.result.makho != 0){
                binding.lnlCommission.visibility = View.GONE
            }
            else{
                binding.lnlCommission.visibility = View.VISIBLE
                val tv_sum = SplashActivity.formatterPrice.format(data.tienhoahong).toString() + " đ"
                binding.tvCommission.setText(tv_sum)
            }
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
        holder.itemView.setOnClickListener{
            var intent = Intent(holder.itemView.context, ShipperStatisticsActivity::class.java)
            intent.putExtra("date",data.ngaynhan)
            holder.itemView.context.startActivity(intent)
        }
    }
}