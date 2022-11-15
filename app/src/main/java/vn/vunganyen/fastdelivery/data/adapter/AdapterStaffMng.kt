package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.databinding.ItemStaffMngBinding
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity


class AdapterStaffMng : RecyclerView.Adapter<AdapterStaffMng.MainViewHolder>() {
    private var listData: List<ListStaffRes> = ArrayList()
    var clickRemoveStaff: ((id : String)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ListStaffRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemStaffMngBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: ListStaffRes) {
            binding.tvStaffId.setText(data.manv)
            binding.tvStaffName.setText(data.hoten)

            if(data.gioitinh.equals("Nam")){
                binding.imvAvtStaff.setBackground(itemView.context.getDrawable(R.drawable.avt2))
            }
            else{
                binding.imvAvtStaff.setBackground(itemView.context.getDrawable(R.drawable.avt1))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemStaffMngBinding.inflate(
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
            var intent = Intent(holder.itemView.context, UpdateStaffActivity::class.java)
           intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.imvRemoveStaff.setOnClickListener{
            clickRemoveStaff?.invoke(data.manv)
        }

    }
}