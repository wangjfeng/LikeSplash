package com.github.jasonhezz.likesplash.data.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.github.jasonhezz.likesplash.R
import com.github.jasonhezz.likesplash.data.User
import com.github.jasonhezz.likesplash.data.viewholder.BaseViewHolder
import com.github.jasonhezz.likesplash.util.extension.loadUrl
import kotlinx.android.synthetic.main.item_user.*

/**
 * Created by JavaCoder on 2017/12/8.
 */
@EpoxyModelClass(layout = R.layout.item_user)
abstract class UserModel : EpoxyModelWithHolder<BaseViewHolder>() {
  @EpoxyAttribute
  var user: User? = null

  override fun bind(holder: BaseViewHolder?) {
    super.bind(holder)
    holder?.avatar?.loadUrl(user?.profile_image?.medium)
    holder?.name?.text = user?.name
    holder?.bio?.text = user?.bio
  }
}