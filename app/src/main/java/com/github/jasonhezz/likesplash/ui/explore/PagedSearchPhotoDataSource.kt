package com.github.jasonhezz.likesplash.ui.explore

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.github.jasonhezz.likesplash.data.Photo
import com.github.jasonhezz.likesplash.data.SearchPhotoResult
import com.github.jasonhezz.likesplash.data.api.ApiResponse
import com.github.jasonhezz.likesplash.data.api.Resource
import com.github.jasonhezz.likesplash.data.api.SearchService
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * Created by JavaCoder on 2017/12/12.
 */
class PagedSearchPhotoDataSource(private val query: String,
    val api: SearchService,
    private val retryExecutor: Executor) : PageKeyedDataSource<Int, Photo>() {

  // keep a function reference for the retry event
  private var retry: (() -> Any)? = null
  /**
   * There is no sync on the state because paging will always call loadInitial first then wait
   * for it to return some success value before calling loadAfter.
   */
  val networkState = MutableLiveData<Resource>()
  val initialLoad = MutableLiveData<Resource>()

  fun retryAllFailed() {
    val prevRetry = retry
    retry = null
    prevRetry?.let {
      retryExecutor.execute {
        it.invoke()
      }
    }
  }

  override fun loadInitial(params: LoadInitialParams<Int>,
      callback: LoadInitialCallback<Int, Photo>) {
    networkState.postValue(Resource.INITIAL)
    initialLoad.postValue(Resource.INITIAL)
    api.searchPhotos(query, 1, params.requestedLoadSize).enqueue(
        object : retrofit2.Callback<SearchPhotoResult> {
          override fun onFailure(call: Call<SearchPhotoResult>, t: Throwable) {
            retry = {
              loadInitial(params, callback)
            }
            networkState.postValue(Resource.LOADED)
            initialLoad.postValue(Resource.LOADED)
            networkState.postValue(Resource.error(t.message ?: "unknown err"))
          }

          override fun onResponse(call: Call<SearchPhotoResult>,
              response: Response<SearchPhotoResult>) {
            if (response.isSuccessful) {
              val apiResponse = ApiResponse(response)
              val items = apiResponse.body?.results ?: emptyList()
              retry = null
              networkState.postValue(Resource.LOADED)
              initialLoad.postValue(Resource.LOADED)
              callback.onResult(items, apiResponse.prevPage, apiResponse.nextPage)
            } else {
              networkState.postValue(Resource.LOADED)
              initialLoad.postValue(Resource.LOADED)
              retry = {
                loadInitial(params, callback)
              }
              networkState.postValue(
                  Resource.error("error code: ${response.code()}"))
            }
          }
        }
    )
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
    networkState.postValue(Resource.MORE)
    api.searchPhotos(query, params.key, params.requestedLoadSize).enqueue(
        object : retrofit2.Callback<SearchPhotoResult> {
          override fun onFailure(call: Call<SearchPhotoResult>, t: Throwable) {
            retry = {
              loadAfter(params, callback)
            }
            networkState.postValue(Resource.error(t.message ?: "unknown err"))
          }

          override fun onResponse(call: Call<SearchPhotoResult>,
              response: Response<SearchPhotoResult>) {
            if (response.isSuccessful) {
              val apiResponse = ApiResponse(response)
              val items = apiResponse.body?.results ?: emptyList()
              retry = null
              networkState.postValue(Resource.LOADED)
              callback.onResult(items, apiResponse.nextPage)
            } else {
              networkState.postValue(Resource.LOADED)
              retry = {
                loadAfter(params, callback)
              }
              networkState.postValue(
                  Resource.error("error code: ${response.code()}"))
            }
          }
        }
    )
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
    // ignored, since we only ever append to our initial load
  }
}