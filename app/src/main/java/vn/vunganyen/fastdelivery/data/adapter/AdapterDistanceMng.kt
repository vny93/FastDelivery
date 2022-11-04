package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.distance.DistanceRes
import vn.vunganyen.fastdelivery.databinding.ItemDistanceBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity


class AdapterDistanceMng : RecyclerView.Adapter<AdapterDistanceMng.MainViewHolder>() {
    private var listData: List<DistanceRes> = ArrayList()
    var clickUpdateDistance: ((id : Int)->Unit)?=null
    var clickRemoveDistance: ((id : Int)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<DistanceRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemDistanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: DistanceRes) {
            binding.edtStartDistance.setText(data.kcbatdau.toString())
            binding.edtEndDistance.setText(data.kcketthuc.toString())
            val price = SplashActivity.formatterPrice.format(data.giatien).toString() + " đ"
            binding.edtPriceDistance.setText(price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemDistanceBinding.inflate(
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
//        holder.binding.deleteBrand.setOnClickListener{
//            click?.invoke(data)
//        }
        holder.binding.imvEdtDistance.setOnClickListener{
            clickUpdateDistance?.invoke(data.makc)
        }
        holder.binding.imvRemoveDistance.setOnClickListener{
            clickRemoveDistance?.invoke(data.makc)
        }
    }
}