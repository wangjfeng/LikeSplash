package com.github.jasonhezz.likesplash.data.model

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.github.jasonhezz.likesplash.R
import com.github.jasonhezz.likesplash.data.Collection
import com.github.jasonhezz.likesplash.data.viewholder.BaseViewHolder
import com.github.jasonhezz.likesplash.util.glide.GlideApp
import kotlinx.android.synthetic.main.item_explore_collection.*

/**
 * Created by JavaCoder on 2017/10/16.
 */
@EpoxyModelClass(layout = R.layout.item_explore_collection)
abstract class ExploreCollectionModel : EpoxyModelWithHolder<BaseViewHolder>() {

  @EpoxyAttribute
  var collection: Collection? = null

  @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
  var collectionClickListener: View.OnClickListener? = null

  override fun bind(holder: BaseViewHolder) {
    super.bind(holder)
    collection?.let {

      val count = it.total_photos ?: 0
      holder.count_tv.text = holder.count_tv.context.resources.
          getQuantityString(R.plurals.photo_plural, count, count)
      holder.title_tv.text = it.title
      GlideApp.with(holder.collection_iv.context).load(it.cover_photo?.urls?.regular)
          .thumbnail(
              Glide.with(holder.collection_iv.context).load(it.cover_photo?.urls?.thumb))
          .materialPlaceHolder(it.cover_photo?.color ?: "#26292c")
          .into(holder.collection_iv)
      holder.card.setOnClickListener(collectionClickListener)
    }
  }

  override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
    return 2
  }
}