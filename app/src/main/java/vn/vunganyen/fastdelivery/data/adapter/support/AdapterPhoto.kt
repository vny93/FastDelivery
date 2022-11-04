package vn.vunganyen.fastdelivery.data.adapter.support

import android.app.Service
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import vn.vunganyen.fastdelivery.data.model.classSupport.Photo
import vn.vunganyen.fastdelivery.databinding.ItemSlideviewBinding

class AdapterPhoto : PagerAdapter {
    lateinit var binding: ItemSlideviewBinding
    lateinit var context : Context
    lateinit var list : List<Photo>

    constructor(context: Context, list: List<Photo>) : super() {
        this.context = context
        this.list = list
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layoutInflater : LayoutInflater = context.applicationContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemSlideviewBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        var photo = list.get(position)
        if(photo != null){
            Glide.with(context).load(photo.resourceId).into(binding.imv1)
        }
        //Add view to Viewgroup
        container.addView(view)
        return view
    }


    override fun getCount(): Int {
        if(list != null){
            return list.size
        }
        return 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}