package com.github.jasonhezz.likesplash.data.model

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.arasthel.spannedgridlayoutmanager.SpanLayoutParams
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.github.jasonhezz.likesplash.R
import com.github.jasonhezz.likesplash.data.Photo
import com.github.jasonhezz.likesplash.data.viewholder.BaseViewHolder
import com.github.jasonhezz.likesplash.util.glide.GlideApp
import kotlinx.android.synthetic.main.item_grid_img.photo_iv

/**
 * Created by JavaCoder on 2018/2/6.
 */
@EpoxyModelClass(layout = R.layout.item_grid_img)
abstract class GridImgModel : EpoxyModelWithHolder<BaseViewHolder>() {

  @EpoxyAttribute
  var photo: Photo? = null

  @EpoxyAttribute
  var spans = 1

  @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
  var tagClickListener: View.OnClickListener? = null

  override fun bind(holder: BaseViewHolder) {
    super.bind(holder)
    GlideApp.with(holder.photo_iv.context)
        .saturateOnLoad()
        .load(photo?.urls?.regular)
        .into(holder.photo_iv)
    holder.containerView?.layoutParams = SpanLayoutParams(SpanSize(spans, spans))
  }
}