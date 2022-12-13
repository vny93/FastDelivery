package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.model.area.GetListAreaRes
import vn.vunganyen.fastdelivery.data.model.detailRegister.UpdateAreaShipperReq
import vn.vunganyen.fastdelivery.data.model.shop.GetShopDetailRes
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.databinding.ItemAreaBinding
import vn.vunganyen.fastdelivery.databinding.ItemShopBinding
import vn.vunganyen.fastdelivery.databinding.ItemStaffMngBinding
import vn.vunganyen.fastdelivery.screens.admin.shopMng.update.UpdateShopMngActivity
import vn.vunganyen.fastdelivery.screens.admin.staffMng.update.UpdateStaffActivity
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity


class AdapterAreaMng : RecyclerView.Adapter<AdapterAreaMng.MainViewHolder>() {
    private var listData: List<GetListAreaRes> = ArrayList()
    var clickUpdateStatusArea: ((data : UpdateAreaShipperReq)->Unit)?=null
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
            if(data.trangthai == 0){
                binding.tvStatusName.setText("Đang hoạt động")
            }
            else{
                binding.tvStatusName.setText("Ngừng hoạt động")
                binding.imvSettingArea.setBackground(itemView.context.getDrawable(R.drawable.ic_baseline_play_circle_24))
            }
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

        holder.binding.imvSettingArea.setOnClickListener{
            var idShipper = SplashActivity.profile.result.manv
            if(data.trangthai == 0){
                //cho cập nhật trạng thái ngưng hoạt động
                var req = UpdateAreaShipperReq(1,idShipper,data.makhuvuc)
                clickUpdateStatusArea?.invoke(req)
            }
            else //cho cập nhật trạng thái hoạt động
            {
                var req = UpdateAreaShipperReq(0,idShipper,data.makhuvuc)
                clickUpdateStatusArea?.invoke(req)
            }

        }

    }
}