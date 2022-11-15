package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.area.GetListAreaRes
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.databinding.ItemAreaBinding
import vn.vunganyen.fastdelivery.databinding.ItemShopBinding
import vn.vunganyen.fastdelivery.databinding.ItemStaffMngBinding
import vn.vunganyen.fastdelivery.screens.admin.shopMng.update.UpdateShopMngActivity
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity


class AdapterAreaMng : RecyclerView.Adapter<AdapterAreaMng.MainViewHolder>() {
    private var listData: List<GetListAreaRes> = ArrayList()
    var clickRemoveArea: ((id : Int)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<GetListAreaRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: GetListAreaRes) {
            binding.tvAreaName.setText(data.tenkhuvuc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)

        holder.binding.imvRemoveArea.setOnClickListener{
            clickRemoveArea?.invoke(data.makhuvuc)
        }

    }
}