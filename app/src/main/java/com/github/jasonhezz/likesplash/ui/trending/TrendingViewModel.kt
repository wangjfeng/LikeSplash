package com.github.jasonhezz.likesplash.ui.trending

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.github.jasonhezz.likesplash.data.Photo
import com.github.jasonhezz.likesplash.repository.Listing
import com.github.jasonhezz.likesplash.repository.RepostioryFactory
import com.github.jasonhezz.likesplash.ui.RxAwareViewModel

/**
 * Created by JavaCoder on 2017/11/27.
 */
class TrendingViewModel : ViewModel() {
  private val result = MutableLiveData<Listing<Photo>>()
  val photos = Transformations.switchMap(result, { it.pagedList })!!
  val networkState = Transformations.switchMap(result, { it.networkState })!!
  val refreshState = Transformations.switchMap(result, { it.refreshState })!!

  init {
    result.postValue(RepostioryFactory.makeTrendingRepository().getTrendingFeed(null,10))
  }

  fun refresh() {
    result.value?.refresh?.invoke()
  }

  fun retry() {
    val listing = result.value
    listing?.retry?.invoke()
  }
}