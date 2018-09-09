package com.github.jasonhezz.likesplash.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.github.jasonhezz.likesplash.data.entities.Collection
import com.github.jasonhezz.likesplash.data.service.SearchService
import com.github.jasonhezz.likesplash.ui.collection.featured.PagedSearchCollectionDataSource

/**
 * Created by JavaCoder on 2017/12/12.
 */
class SearchCollectionDataSourceFactory(
        private val query: String,
        private val api: SearchService
) : DataSource.Factory<Int, Collection>() {
    val sourceLiveData = MutableLiveData<PagedSearchCollectionDataSource>()
    override fun create(): DataSource<Int, Collection> {
        val source = PagedSearchCollectionDataSource(query , api)
        sourceLiveData.postValue(source)
        return source
    }
}