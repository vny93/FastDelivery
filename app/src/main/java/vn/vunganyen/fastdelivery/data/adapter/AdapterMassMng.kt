package vn.vunganyen.fastdelivery.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.fastdelivery.data.model.mass.MassRes
import vn.vunganyen.fastdelivery.databinding.ItemMassBinding
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity


class AdapterMassMng : RecyclerView.Adapter<AdapterMassMng.MainViewHolder>() {
    private var listData: List<MassRes> = ArrayList()
    var clickUpdateMass: ((id : Int)->Unit)?=null
    var clickRemoveMass: ((id : Int)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<MassRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemMassBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: MassRes) {
            binding.edtStartMass.setText(data.klbatdau.toString())
            binding.edtEndMass.setText(data.klketthuc.toString())
            val price = SplashActivity.formatterPrice.format(data.giatien).toString() + " đ"
            binding.edtPriceMass.setText(price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemMassBinding.inflate(
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
        holder.binding.imvEdtMass.setOnClickListener{
            clickUpdateMass?.invoke(data.makl)
        }
        holder.binding.imvRemoveMass.setOnClickListener{
            clickRemoveMass?.invoke(data.makl)
        }

    }
}