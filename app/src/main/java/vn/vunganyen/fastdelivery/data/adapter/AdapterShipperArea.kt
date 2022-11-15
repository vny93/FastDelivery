package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.fastdelivery.data.model.staff.ShipperAreaRes
import vn.vunganyen.fastdelivery.data.model.warehouse.GetParcelWhRes
import vn.vunganyen.fastdelivery.databinding.ItemShipperAreaBinding
import java.util.*


class AdapterShipperArea : RecyclerView.Adapter<AdapterShipperArea.MainViewHolder>() {
    private var listData: List<ShipperAreaRes> = ArrayList()
    var clickRadio: ((data : ShipperAreaRes)->Unit)?=null
    public var selectedPosition = -1
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ShipperAreaRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemShipperAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: ShipperAreaRes) {
            binding.radioNameShipper.setText(data.hoten)
            binding.tvNumberParcel.setText(data.sl.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemShipperAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = listData[position]
        holder.bindItem(data)
        holder.binding.radioNameShipper.setChecked(position == selectedPosition)
        holder.binding.radioNameShipper.setOnClickListener { v ->
            if (position == selectedPosition) {
                holder.binding.radioNameShipper.setChecked(false)
                selectedPosition = -1
            } else {
                selectedPosition = position
                notifyDataSetChanged()
                clickRadio?.invoke(data)
            }
        }

    }

}