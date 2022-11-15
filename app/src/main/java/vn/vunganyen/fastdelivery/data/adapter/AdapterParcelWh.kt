package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhRes
import vn.vunganyen.fastdelivery.databinding.ItemCheckboxWarehouseBinding
import java.util.*
import kotlin.collections.ArrayList


class AdapterParcelWh : RecyclerView.Adapter<AdapterParcelWh.MainViewHolder>() {
    private var listData: List<GetParcelWhRes> = ArrayList()
    var clickCheckboxTrue: ((data : GetParcelWhRes, count : Int)->Unit)?=null
    var clickCheckboxFalse: ((data : GetParcelWhRes, count : Int)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<GetParcelWhRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemCheckboxWarehouseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: GetParcelWhRes) {
            binding.checkboxWarehouseName.setText(data.tenkho)
            binding.tvNumberParcel.setText(data.sl.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemCheckboxWarehouseBinding.inflate(
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
        }
        holder.binding.checkboxWarehouseName.setOnClickListener{
            if(holder.binding.checkboxWarehouseName.isChecked == true){
                clickCheckboxTrue?.invoke(data,1)
            }
            else{
                clickCheckboxFalse?.invoke(data,(-1))
            }
        }

    }
}