package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.salary.CollectionRes
import vn.vunganyen.fastdelivery.databinding.ItemCollectionBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import kotlin.collections.ArrayList


class AdapterShipperCollection : RecyclerView.Adapter<AdapterShipperCollection.MainViewHolder>() {
    private var listData: List<CollectionRes> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<CollectionRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: CollectionRes) {
            binding.tvId.setText(data.mabk.toString())
            binding.tvName.setText(data.tench)
            val price1 = SplashActivity.formatterPrice.format(data.sotien).toString() + " đ"
            binding.tvPrice1.setText(price1)
            val price2 = SplashActivity.formatterPrice.format(data.phigiao).toString() + " đ"
            binding.tvPrice2.setText(price2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
    }
}