package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.databinding.ItemShopBinding
import vn.vunganyen.fastdelivery.databinding.ItemStaffMngBinding
import vn.vunganyen.fastdelivery.screens.admin.shopMng.update.UpdateShopMngActivity
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity


class AdapterShopMng : RecyclerView.Adapter<AdapterShopMng.MainViewHolder>() {
    private var listData: List<GetShopDetailRes> = ArrayList()
    var clickRemoveStaff: ((id : String)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<GetShopDetailRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: GetShopDetailRes) {
            binding.tvShopId.setText(data.mach)
            binding.tvShopName.setText(data.tench)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemShopBinding.inflate(
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
            var intent = Intent(holder.itemView.context, UpdateShopMngActivity::class.java)
           intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.imvRemoveStaff.setOnClickListener{
         //   clickRemoveStaff?.invoke(data.manv)
        }

    }
}