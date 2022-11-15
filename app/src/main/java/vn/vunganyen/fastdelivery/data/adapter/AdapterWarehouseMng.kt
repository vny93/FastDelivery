package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.data.model.warehouse.WarehouseRes
import vn.vunganyen.fastdelivery.databinding.ItemStaffMngBinding
import vn.vunganyen.fastdelivery.databinding.ItemWarehouseBinding
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity
import vn.vunganyen.fastdelivery.screens.admin.warehouseMng.update.UpdateWarehouseActivity


class AdapterWarehouseMng : RecyclerView.Adapter<AdapterWarehouseMng.MainViewHolder>() {
    private var listData: List<WarehouseRes> = ArrayList()
    var clickRemoveWarehouse: ((id : String)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<WarehouseRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemWarehouseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: WarehouseRes) {
            binding.tvWarehouseId.setText(data.makho.toString())
            binding.tvWarehouseName.setText(data.tenkho)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemWarehouseBinding.inflate(
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
            var intent = Intent(holder.itemView.context, UpdateWarehouseActivity::class.java)
           intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.imvRemoveStaff.setOnClickListener{
            clickRemoveWarehouse?.invoke(data.makho.toString())
        }

    }
}