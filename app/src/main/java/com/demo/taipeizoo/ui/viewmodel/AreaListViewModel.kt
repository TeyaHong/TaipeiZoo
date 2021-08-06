package com.demo.taipeizoo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.taipeizoo.data.repository.ApiRepository
import com.demo.taipeizoo.ui.adapter.AreaRowData
import com.demo.taipeizoo.ui.adapter.RowData
import com.demo.taipeizoo.utils.Resource
import kotlinx.coroutines.launch

/**
 * 館區列表 ViewModel
 *
 * Created by TeyaHong on 2021/8/4
 */
class AreaListViewModel : ViewModel() {
    private val apiRepository: ApiRepository by lazy { ApiRepository() }

    private val _liveData = MutableLiveData<Resource<List<RowData>>>()
    val liveData: LiveData<Resource<List<RowData>>> = _liveData

    fun getAreas() {
        viewModelScope.launch {
            _liveData.value = Resource.loading()
            try {
                val newData = mutableListOf<RowData>()
                val list = apiRepository.getAreas().result.results
                for (i in list.indices) {
                    newData.add(AreaRowData(list[i]))
                }
                _liveData.value = Resource.success(data = newData)
            } catch (e: Exception) {
                _liveData.value = Resource.error(error = e.message)
            }
        }
    }
}