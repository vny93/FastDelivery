package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.salary.CollectionRes
import vn.vunganyen.fastdelivery.databinding.ItemAdminCollectionBinding
import vn.vunganyen.fastdelivery.databinding.ItemCollectionBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import kotlin.collections.ArrayList


class AdapterAdminCollection : RecyclerView.Adapter<AdapterAdminCollection.MainViewHolder>() {
    private var listData: List<CollectionRes> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<CollectionRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemAdminCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: CollectionRes) {
            binding.tvId.setText(data.mabk.toString())
            binding.tvIdShop.setText(data.mach)
            binding.tvName.setText(data.tench)
            val price1 = SplashActivity.formatterPrice.format(data.sotien).toString() + " đ"
            binding.tvPrice2.setText(price1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemAdminCollectionBinding.inflate(
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